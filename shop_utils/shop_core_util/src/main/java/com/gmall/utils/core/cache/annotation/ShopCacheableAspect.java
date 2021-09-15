package com.gmall.utils.core.cache.annotation;

import com.gmall.util.common.exception.SleepUtils;
import com.gmall.utils.core.constant.RedisConst;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


/**
 * @version v1.0
 * @ClassName ShopCacheableAspect
 * @Description TODO
 * @Author Q
 */
@Aspect
@Order(1)
@Component //注册Bean
public class ShopCacheableAspect {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ShopCacheableAspect.class);
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    //扫描@Shopacaheable注解
    @Around("@annotation(com.gmall.utils.core.cache.annotation.ShopCacheable)")
    public Object cacheableDoAround(ProceedingJoinPoint target) throws Throwable {
        Object result = null;
        //1.首要做的事情是把redis缓存的key拼接好 拿到前缀和skuId的信息 sku:info+skuId
        //2.首先需要拿到目标方法
        MethodSignature signature = (MethodSignature) target.getSignature();
        Method method = signature.getMethod();
        //3.通过目标方法拿到目标方法上面的注解
        ShopCacheable shopCacheable = method.getAnnotation(ShopCacheable.class);
        //4.获取注解上的前缀信息
        String prefix = shopCacheable.prefix();
        //5.目标方法上面的参数
        Object[] args = target.getArgs();
        //6.拼接key
        String key = prefix + args[0];
        //a.从缓存中拿取skuInfo的信息
        result = cacheHit(key);
        //result == null ? 查数据库 ： 直接返回
        if (ObjectUtils.isEmpty(result)) {
            //获取锁🔒
            RLock rLock = redissonClient.getLock(key + ":lock");
            try {
                //判断是否拿到锁
                boolean acquireLock = rLock.tryLock(RedisConst.WAITTIN_GET_LOCK_TIME, -1, TimeUnit.SECONDS);
                //拿到锁
                if (acquireLock) {
                    log.info("{}没拿到锁", Thread.currentThread());
                    //拿到锁，执行目标方法
                    result = target.proceed();
                    //result == null ? 缓存穿透 ： 先见数据缓存到Redis再返回
                    if (ObjectUtils.isEmpty(result)) {
                        //创建一个空值对象放入Redis中
                        Object bankObj = new Object();
                        //防止死锁对key设置过期时间
                        redisTemplate.opsForValue().set(key, bankObj, RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
                        //返回这个不存在的对象给前端
                        return bankObj;
                    }
                    //将查出来的数据缓存到Redis中，并设置过期时间
                    //SleepUtils.sleepSecond(20);
                    redisTemplate.opsForValue().set(key, result, RedisConst.SKUKEY_TIMEOUT, TimeUnit.SECONDS);
                    //返回给前端
                    return result;
                } else {
                    //其他进程先进行等待
                    SleepUtils.sleepMills(500);
                    log.info("{}没拿到锁", Thread.currentThread());
                    //自旋
                    return cacheableDoAround(target);
                    //其他进程直接查询缓存
                    //return cacheHit(key);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            } finally {
                rLock.unlock();
            }
        } else {
            return result;
        }
        return target.proceed(args);
    }

    //查询缓存
    private Object cacheHit(String skuKey) {
        return redisTemplate.opsForValue().get(skuKey);
    }


}
