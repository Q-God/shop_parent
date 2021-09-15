package com.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gmall.entity.BaseCategoryView;
import com.gmall.entity.ProductSalePropertyKey;
import com.gmall.entity.SkuImage;
import com.gmall.entity.SkuInfo;
import com.gmall.product.mapper.*;
import com.gmall.product.service.SkuDetailService;
import com.gmall.util.common.exception.SleepUtils;
import com.gmall.utils.core.cache.annotation.ShopCacheable;
import com.gmall.utils.core.constant.RedisConst;
import org.slf4j.Logger;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ClassName SkuDetailServiceImpl
 * @Description TODO
 * @Author Q
 */
@Service
public class SkuDetailServiceImpl implements SkuDetailService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SkuDetailServiceImpl.class);
    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Autowired
    private ProductSalePropertyKeyMapper productSalePropertyKeyMapper;

    @Autowired
    private SkuSalePropertyValueMapper skuSalePropertyValueMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据skuId查询skuInfo
     *
     * @param skuId
     * @return
     */
    @ShopCacheable("sku:info:")
    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        log.info("{}执行了getSkuInfo", Thread.currentThread());
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (ObjectUtils.isEmpty(skuInfo)) {
            //根据skuId查询图片列表集合 拒绝硬编码
            LambdaQueryWrapper<SkuImage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SkuImage::getSkuId, skuId);
            List<SkuImage> skuImageList = skuImageMapper.selectList(queryWrapper);
            skuInfo.setSkuImageList(skuImageList);
        }
        //SkuInfo skuInfo = getSkuInfoFromCache(skuId);
        return skuInfo;
    }

    private SkuInfo getSkuInfoFromDB(Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (ObjectUtils.isEmpty(skuInfo)) {
            //根据skuId查询图片列表集合 拒绝硬编码
            LambdaQueryWrapper<SkuImage> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SkuImage::getSkuId, skuId);
            List<SkuImage> skuImageList = skuImageMapper.selectList(queryWrapper);
            skuInfo.setSkuImageList(skuImageList);
        }
        return skuInfo;
    }


    /**
     * 通过三级Id查询分类信息
     *
     * @param category3Id
     * @return
     */
    @Override
    public BaseCategoryView getBaseCategoryViewByCategory3Id(Long category3Id) {
        return baseCategoryViewMapper.selectById(category3Id);
    }

    /**
     * 根据productId,skuId查询商品销售属性key与value
     *
     * @param productId
     * @param skuId
     * @return
     */
    @Override
    public List<ProductSalePropertyKey> getSkuSalePropertyKeyAndValue(Long productId, Long skuId) {
        return productSalePropertyKeyMapper.selectSkuSalePropertyKeyAndValue(productId, skuId);
    }

    /**
     * 获取Sku价格 BigDecimal防止失精度
     *
     * @param skuId
     * @return
     */
    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        SkuDetailService proxySkuDetailService = (SkuDetailService) AopContext.currentProxy();
        System.out.println(proxySkuDetailService.getClass());
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (!ObjectUtils.isEmpty(skuInfo)) {
            return skuInfo.getPrice();
        }
        return new BigDecimal("0");
    }

    /**
     * 通过SpuId获取相应的Json字符串
     *
     * @param productId
     * @return
     */
    @Override
    public Map getSkuSalePropertyValueId(Long productId) {
        List<Map> mapList = skuSalePropertyValueMapper.selectSkuSalePropertyValueId(productId);
        if (!CollectionUtils.isEmpty(mapList)) {
            Map map = mapList.stream().parallel().collect(Collectors.toMap(
                    mapKey -> mapKey.get("sale_property_value_id"), mapValue -> mapValue.get("sku_id")
            ));
            return map;
        }
        return null;
    }

    /**
     * Redis + Lua 实现
     *
     * @param skuId
     * @return
     */
    private SkuInfo getSkuInfoFromCache(Long skuId) {
        //拼接skuKey
        String skuKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKUKEY_SUFFIX;
        //先查询缓存中有没有SkuIndfo
        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);
        //skuInfo == null ？ 查数据库并加到缓存，直接返回
        if (ObjectUtils.isEmpty(skuInfo)) {
            //防止数据库高并发情况下死掉 设lockKey
            String lockKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKULOCK_SUFFIX;
            //设置UUID
            String uuid = UUID.randomUUID().toString();
            //查询是否获取锁
            boolean acquireLock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, RedisConst.LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
            //获得锁，查询数据库
            if (acquireLock) {
                //从数据库中查询出来
                SkuInfo skuInfoFromDB = getSkuInfoFromDB(skuId);
                if (ObjectUtils.isEmpty(skuInfoFromDB)) {
                    //数据库中没有该数据，设置空置对返回，防止高并发情况下缓存穿透
                    SkuInfo bankSKuInfo = new SkuInfo();
                    redisTemplate.opsForValue().set(skuKey, bankSKuInfo, RedisConst.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
                    //返回空值对象给前端
                } else {
                    //将数据库中的结果存入缓存
                    redisTemplate.opsForValue().set(skuKey, skuInfoFromDB, RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
                    //执行完释放锁过程中存在原子性问题，需要借助lua脚本完善
                    String luaScript = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

                    //定义RedisScript对象用来存储lua脚本
                    DefaultRedisScript redisScript = new DefaultRedisScript();
                    redisScript.setScriptText(luaScript);
                    //设置返回类型
                    redisScript.setResultType(Long.class);
                    redisTemplate.execute(redisScript, Arrays.asList(skuKey), uuid);
                    return skuInfoFromDB;
                }
            } else {
                //其他线程
                SleepUtils.sleepMills(500);

                //自旋
                return getSkuInfo(skuId);
            }

        }

        return skuInfo;
    }
}
