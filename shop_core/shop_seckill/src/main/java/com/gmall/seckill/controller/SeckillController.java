package com.gmall.seckill.controller;

import com.gmall.entity.*;
import com.gmall.seckill.service.SeckillProductService;
import com.gmall.seckill.util.DateUtils;
import com.gmall.user.client.UserFeignClient;
import com.gmall.util.common.result.RetVal;
import com.gmall.util.common.result.RetValCodeEnum;
import com.gmall.util.common.util.AuthContextHolder;
import com.gmall.utils.core.constant.MqConst;
import com.gmall.utils.core.constant.RedisConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @version v1.0
 * @ClassName SeckillController
 * @Description TODO
 * @Author Q
 */
@RestController

public class SeckillController {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 查询所有的商品详情列表
     *
     * @return
     */
    @GetMapping("/selectAllSeckillProduct")
    public RetVal selectAllSeckillProduct() {
        List<SeckillProduct> seckillProductList = seckillProductService.selectAllSeckillProduct();
        return RetVal.ok(seckillProductList);
    }

    /**
     * 秒杀商品详情编写
     *
     * @param skuId
     * @return
     */
    @GetMapping("getSecKillProductBySkuId/{skuId}")
    public RetVal getSecKillProductBySkuId(@PathVariable Long skuId) {
        SeckillProduct seckillProduct = seckillProductService.getSecKillProductBySkuId(skuId);
        return RetVal.ok(seckillProduct);
    }

    /**
     * 生成必购码
     *
     * @param skuId
     * @param request
     * @return
     */
    @GetMapping("generateSeckillCode/{skuId}")
    public RetVal generateSeckillCode(@PathVariable Long skuId, HttpServletRequest request) {
        //判断用户是否登录
        String userId = AuthContextHolder.getUserId(request);
        if (!StringUtils.isEmpty(userId)) {
            //查！
            SeckillProduct secKillProduct = seckillProductService.getSecKillProductBySkuId(skuId);
            Date now = new Date();
            if (DateUtils.dateCompare(secKillProduct.getStartTime(), now) &&
                    DateUtils.dateCompare(now, secKillProduct.getEndTime())) {
                //生成必购码
                String seckillCode = DigestUtils.md5DigestAsHex(userId.getBytes());
                return RetVal.ok(seckillCode);
            }
        }
        return RetVal.fail().message("获取必购码失败,请检查登陆状态");
    }

    /**
     * 秒杀预下单
     *
     * @param skuId
     * @param seckillCode
     * @param request
     * @return
     */
    @PostMapping("prepareSeckill/{skuId}")
    public RetVal prepareSeckill(@PathVariable Long skuId, String seckillCode, HttpServletRequest request) {
        String userId = AuthContextHolder.getUserId(request);
        if (!DigestUtils.md5DigestAsHex(userId.getBytes()).equals(seckillCode)) {
            //必购码不匹配  作弊
            return RetVal.build(null, RetValCodeEnum.SECKILL_ILLEGAL);
        }
        //请求合法，判断秒杀商品状态
        String status = (String) redisTemplate.boundValueOps(RedisConst.SECKILL_STATE_PREFIX + skuId).get();
        if (StringUtils.isEmpty(status)) {
            return RetVal.build(null, RetValCodeEnum.SECKILL_ILLEGAL);
        }

        //可以秒杀了
        if (RedisConst.CAN_SECKILL.equals(status)) {
            UserSeckillSkuInfo userSeckillSkuInfo = new UserSeckillSkuInfo();
            userSeckillSkuInfo.setUserId(userId);
            userSeckillSkuInfo.setSkuId(skuId);
            rabbitTemplate.convertAndSend(MqConst.PREPARE_SECKILL_EXCHANGE, MqConst.PREPARE_SECKILL_ROUTE_KEY, userSeckillSkuInfo);
        } else {
            return RetVal.build(null, RetValCodeEnum.SECKILL_FINISH);
        }
        return RetVal.ok();
    }

    @GetMapping("hasQualified/{skuId}")
    public RetVal hasQualified(@PathVariable Long skuId, HttpServletRequest request) {
        String userId = AuthContextHolder.getUserId(request);
        return seckillProductService.hasQualified(skuId, userId);
    }

    /**
     * 秒杀订单确认页
     *
     * @param request
     * @return
     */
    @GetMapping("seckillConfirm")
    public RetVal seckillConfirm(HttpServletRequest request) {
        String userId = AuthContextHolder.getUserId(request);

        PrepareSeckillOrder prepareSeckillOrder = (PrepareSeckillOrder) redisTemplate.boundHashOps(RedisConst.PREPARE_SECKILL_USERID_ORDER).get(userId);
        if (ObjectUtils.isEmpty(prepareSeckillOrder)) {
            return RetVal.fail().message("非法请求");
        }
        List<UserAddress> userAddressList = userFeignClient.getAddressByUserId(userId);
        SeckillProduct seckillProduct = prepareSeckillOrder.getSeckillProduct();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setSkuId(seckillProduct.getSkuId());
        orderDetail.setSkuNum(seckillProduct.getSkuName());
        orderDetail.setImgUrl(seckillProduct.getSkuDefaultImg());
        orderDetail.setSkuNum(prepareSeckillOrder.getBuyNum().toString());
        orderDetail.setOrderPrice(seckillProduct.getCostPrice());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        //打包返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userAddressList", userAddressList);
        resultMap.put("orderDetailList", orderDetailList);
        resultMap.put("totalMoney", seckillProduct.getCostPrice());
        return RetVal.ok(resultMap);

    }
}
