package com.gmall.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gmall.entity.PrepareSeckillOrder;
import com.gmall.entity.SeckillProduct;
import com.gmall.entity.UserSeckillSkuInfo;
import com.gmall.seckill.mapper.SeckillProductMapper;
import com.gmall.seckill.service.SeckillProductService;
import com.gmall.seckill.util.DateUtils;
import com.gmall.util.common.result.RetVal;
import com.gmall.util.common.result.RetValCodeEnum;
import com.gmall.utils.core.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @ClassName SeckillProductServiceImpl
 * @Description TODO
 * @Author Q
 */
@Service
public class SeckillProductServiceImpl implements SeckillProductService {

    @Autowired
    private SeckillProductMapper seckillProductMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void update(SeckillProduct seckillProduct) {
        seckillProductMapper.updateById(seckillProduct);
    }

    /**
     * 查询当日记录
     *
     * @return
     */
    @Override
    public List<SeckillProduct> selectDayRecords() {
        QueryWrapper<SeckillProduct> wrapper = new QueryWrapper();
        wrapper.eq("status", 1);
        wrapper.gt("stock_count", 0);
        wrapper.eq("DATE_FORMAT(start_time,'%Y-%m-%d')", DateUtils.formatDate(new Date()));
        return seckillProductMapper.selectList(wrapper);
    }

    /**
     * 查询所有的秒杀商品列表
     *
     * @return
     */
    @Override
    public List<SeckillProduct> selectAllSeckillProduct() {
        return redisTemplate.boundHashOps(RedisConst.SECKILL_PRODUCT).values();
    }

    /**
     * 查询所有失效记录
     *
     * @return
     */
    @Override
    public List<SeckillProduct> selectAllExpired() {
        QueryWrapper<SeckillProduct> wrapper = new QueryWrapper<>();
        wrapper.eq("status", 1);
        wrapper.le("end_time", new Date());
        return seckillProductMapper.selectList(wrapper);
    }

    /**
     * 秒杀商品详情页面编写
     *
     * @param skuId
     * @return
     */
    @Override
    public SeckillProduct getSecKillProductBySkuId(Long skuId) {
        return (SeckillProduct) redisTemplate.boundHashOps(RedisConst.SECKILL_PRODUCT).get(skuId.toString());
    }

    /**
     * 预下单
     *
     * @param userSeckillSkuInfo
     */
    @Override
    public void prepareSeckill(UserSeckillSkuInfo userSeckillSkuInfo) {
        String userId = userSeckillSkuInfo.getUserId();
        Long skuId = userSeckillSkuInfo.getSkuId();

        String state = (String) redisTemplate.opsForValue().get(RedisConst.SECKILL_STATE_PREFIX + skuId);

        if (RedisConst.CAN_NOT_SECKILL.equals(state)) {
            return;
        }

        boolean flag = redisTemplate.opsForValue().setIfAbsent(RedisConst.PREPARE_SECKILL_USERID_SKUID + userId, skuId, RedisConst.PREPARE_SECKILL_LOCK_TIME, TimeUnit.SECONDS);
        if (!flag) {
            return;
        }

        String stockSkuId = (String) redisTemplate.boundListOps(RedisConst.SECKILL_STOCK_PREFIX + skuId).rightPop();
        if (StringUtils.isEmpty(stockSkuId)) {
            redisTemplate.convertAndSend(RedisConst.PREPARE_SECKILL_USERID_ORDER, skuId + ":" + RedisConst.CAN_NOT_SECKILL);
            return;
        }
        PrepareSeckillOrder prepareSeckillOrder = new PrepareSeckillOrder();
        prepareSeckillOrder.setUserId(userId);
        prepareSeckillOrder.setBuyNum(1);
        SeckillProduct seckillProduct = getSecKillProductBySkuId(skuId);
        prepareSeckillOrder.setSeckillProduct(seckillProduct);
        prepareSeckillOrder.setPrepareOrderCode(DigestUtils.md5DigestAsHex((userId + skuId).getBytes()));
        redisTemplate.boundHashOps(RedisConst.PREPARE_SECKILL_USERID_ORDER).put(userId, prepareSeckillOrder);
        updtaeSeckillStock(skuId);
    }

    /**
     * 检查用户是否有资格
     *
     * @param skuId
     * @param userId
     * @return
     */
    @Override
    public RetVal hasQualified(Long skuId, String userId) {
        //a.如果预下单中有用户的信息 prepare:seckill:userId:skuId3
        boolean isExist = redisTemplate.hasKey(RedisConst.PREPARE_SECKILL_USERID_SKUID + userId);
        if (isExist) {
            PrepareSeckillOrder prepareSeckillOrder = (PrepareSeckillOrder) redisTemplate.boundHashOps(RedisConst.PREPARE_SECKILL_USERID_ORDER).get(userId);
            if (!ObjectUtils.isEmpty(prepareSeckillOrder)) {
                return RetVal.build(prepareSeckillOrder, RetValCodeEnum.SECKILL_ORDER_SUCCESS);
            }
        }
        return RetVal.build(null, RetValCodeEnum.SECKILL_RUN);
    }

    private void updtaeSeckillStock(Long skuId) {
        //拿取商品剩余库存量
        Long stockCount = redisTemplate.boundListOps(RedisConst.SECKILL_STOCK_PREFIX + skuId).size();
        //更新数据库 设置更新频率 自定义一个规则
        if (stockCount % 2 == 0) {
            SeckillProduct seckillProduct = getSecKillProductBySkuId(skuId);
            seckillProduct.setStockCount(stockCount.intValue());
            seckillProductMapper.updateById(seckillProduct);
            //更新redis缓存 为了给用户看 有没有机会秒到
            redisTemplate.boundHashOps(RedisConst.SECKILL_PRODUCT).put(skuId, seckillProduct);
        }
    }

}
