package com.gmall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gmall.entity.BaseBrand;
import com.gmall.product.mapper.BaseBrandMapper;
import com.gmall.product.service.BaseBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

/**
 * @version v1.0
 * @ClassName BaseBrandServiceImpl
 * @Description TODO
 * @Author Q
 */
@Service
public class BaseBrandServiceImpl extends ServiceImpl<BaseBrandMapper, BaseBrand> implements BaseBrandService {

    @Autowired
    private BaseBrandMapper baseBrandMapper;

    private final Predicate<String> stringPredicate = String::isEmpty;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 商标品牌分页
     *
     * @param pageParam
     * @return
     */
    @Override
    public IPage<BaseBrand> getBaseBrandPage(Page<BaseBrand> pageParam) {
        QueryWrapper<BaseBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        return baseBrandMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 通过brandId获取品牌信息
     *
     * @param brandId
     * @return
     */
    @Override
    public BaseBrand getBaseBrandById(Long brandId) {
        return baseBrandMapper.selectById(brandId);
    }


//    public synchronized void setNum() {
//        String value = (String) redisTemplate.opsForValue().get("num");
//        if (StringUtils.isEmpty(value)) {
//            redisTemplate.opsForValue().set("num", "1");
//        }
//        int num = Integer.parseInt(value);
//
//        redisTemplate.opsForValue().set("num", String.valueOf(++num));
//    }


    /**
     * 测试
     */
//    @Override
//    public synchronized void setNum() {
//
//        String uuid = UUID.randomUUID().toString();
//        boolean acquireLock = redisTemplate.opsForValue().setIfAbsent("lock", "ok");
//        //如果拿到了锁 执行业务逻辑
//        if (acquireLock) {
//            String value = (String) redisTemplate.opsForValue().get("num");
//            if (StringUtils.isBlank(value)) {
//                redisTemplate.opsForValue().set("num", 1);
//                redisTemplate.delete("lock");
//            }
//            int num = Integer.parseInt(value);
//
//            redisTemplate.opsForValue().set("num", num++);
//            String redisUUID = (String) redisTemplate.opsForValue().get("lock");
//            if (uuid.equals(redisUUID)) {
//                redisTemplate.delete("lock");
//            }
//        } else {
//            //没拿到锁
//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            //自旋锁
//            setNum();
//        }
//    }

    /**
     * redis + lua 实现分布式锁
     *
     * @param skuId
     */

//    public SkuInfo getSkuInfoFromRedis(Long skuId) {
//        String skuKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKUKEY_PREFIX;
//        //从缓存中获取数据
//        SkuInfo skuInfo = (SkuInfo) redisTemplate.opsForValue().get(skuKey);
//        if (ObjectUtils.isEmpty(skuInfo)) {
//            String lockKey = RedisConst.SKUKEY_PREFIX + skuId + RedisConst.SKULOCK_SUFFIX;
//            //设置UUID
//            String uuid = UUID.randomUUID().toString();
//            boolean acquireLock = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, RedisConst.WAITTIN_GET_LOCK_TIME, TimeUnit.SECONDS);
//            if (acquireLock) {
//                skuInfo = getSkuInfoFromRedis(skuId);
//
//                if (ObjectUtils.isEmpty(skuInfo)) {
//                    SkuInfo emptySkuInfo = new SkuInfo();
//
//                    redisTemplate.opsForValue().set(skuKey, emptySkuInfo, RedisConst.SKUKEY_TEMPORARY_TIMEOUT, TimeUnit.SECONDS);
//
//                    return emptySkuInfo;
//                }
//
//                redisTemplate.opsForValue().set(skuKey, skuInfo, RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
//
//                String lua = "if redis.call('get', KEYS[1]) == AVG[1] then return redis.call('del', KEYS[1]) else return 0 end";
//
//                DefaultRedisScript defaultRedisScript = new DefaultRedisScript();
//
//                defaultRedisScript.setScriptText(lua);
//
//                defaultRedisScript.setResultType(Long.class);
//                redisTemplate.execute(defaultRedisScript, Arrays.asList(lockKey), uuid);
//                return skuInfo;
//            } else {
//                SleepUtils.sleepSecond(1);
//                return getSkuInfoFromRedis(skuId);
//            }
//        } else {
//            return skuInfo;
//        }
//    }
}
