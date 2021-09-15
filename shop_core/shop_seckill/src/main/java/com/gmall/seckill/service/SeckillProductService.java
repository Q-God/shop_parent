package com.gmall.seckill.service;

import com.gmall.entity.SeckillProduct;
import com.gmall.entity.UserSeckillSkuInfo;
import com.gmall.util.common.result.RetVal;

import java.util.List;

/**
 * @version v1.0
 * @ClassName SeckillProductService
 * @Description TODO
 * @Author Q
 */
public interface SeckillProductService {

    void update(SeckillProduct seckillProduct);
    /**
     * 查询当日记录
     *
     * @return
     */
    List<SeckillProduct> selectDayRecords();

    /**
     * 查询所有失效记录
     *
     * @return
     */
    List<SeckillProduct> selectAllExpired();

    /**
     * 查询所有的秒杀商品列表
     *
     * @return
     */
    List<SeckillProduct> selectAllSeckillProduct();

    /**
     * 秒杀商品详情页面编写
     *
     * @param skuId
     * @return
     */
    SeckillProduct getSecKillProductBySkuId(Long skuId);

    /**
     * 检查用户是否有资格
     *
     * @param skuId
     * @param userId
     * @return
     */
    RetVal hasQualified(Long skuId, String userId);

    /**
     * 预下单资格
     *
     * @param userSeckillSkuInfo
     */
    void prepareSeckill(UserSeckillSkuInfo userSeckillSkuInfo);
}
