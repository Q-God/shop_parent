package com.gmall.order.controller;

import com.gmall.entity.OrderInfo;
import com.gmall.order.service.OrderInfoService;
import com.gmall.util.common.result.RetVal;
import com.gmall.util.common.util.AuthContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName OrderInfoController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/order")
public class OrderInfoController {


    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 用户确认页面
     *
     * @param request 获取userId用
     * @return
     */
    @GetMapping("/confirm")
    public Map<String, Object> confirm(HttpServletRequest request) {
        //此时一定是登录了的，直接查询uerId
        String userId = AuthContextHolder.getUserId(request);
        return orderInfoService.confirm(userId);
    }

    //2.根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public OrderInfo getOrderInfo(@PathVariable Long orderId) {
        return orderInfoService.getOrderInfo(orderId);

    }

    @PostMapping("/submitOrder")
    public RetVal submitOrder(@RequestBody OrderInfo orderInfo, HttpServletRequest request) {
        String userId = AuthContextHolder.getUserId(request);
        //a.返回无刷新不能重复提交订单
        String tradeNo = request.getParameter("tradeNo");
        //判断该交易号是否同redis里面的一致
        boolean flag = orderInfoService.checkTradeNo(tradeNo, userId);
        if (!flag) {
            return RetVal.fail().message("不能无刷新回退提交订单");
        }
        //删除交易号
        orderInfoService.deleteTradeNo(userId);

        //验证库存和价格
        List<String> warningInfoList = orderInfoService.checkStockAndPrice(userId, orderInfo);
        //如果有警告信息
        if (warningInfoList.size() > 0) {
            return RetVal.fail().message(StringUtils.join(warningInfoList, ","));
        }
        //c.保存订单信息(基本信息/详情信息)
        orderInfo.setUserId(Long.parseLong(userId));
        Long orderId = orderInfoService.saveOrderAndDetail(orderInfo);
        return RetVal.ok(orderId);
    }
}
