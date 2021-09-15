package com.gmall.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gmall.cart.mapper.CartInfoMapper;
import com.gmall.cart.service.CartInfoService;
import com.gmall.entity.CartInfo;
import com.gmall.entity.SkuInfo;
import com.gmall.product.client.ProductFeignClient;
import com.gmall.utils.core.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ClassName CartInfoServiceImpl
 * @Description 购物车微服务实现类
 * @Author Q
 */
@Service
public class CartInfoServiceImpl implements CartInfoService {

    private final Supplier<LambdaQueryWrapper<CartInfo>> wrapperSupplier = LambdaQueryWrapper::new;
    private final AsyncCartInfoService asyncCartInfoService = new AsyncCartInfoServiceImpl();
    @Autowired
    private CartInfoMapper cartInfoMapper;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加到购物车
     *
     * @param userId
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(String userId, Long skuId, Integer skuNum) {
        //a.先查询数据库中是否有该商品购物车信息
        LambdaQueryWrapper<CartInfo> queryWrapper = wrapperSupplier.get();
        queryWrapper.eq(CartInfo::getSkuId, skuId);
        queryWrapper.eq(CartInfo::getUserId, userId);
        CartInfo dbCartInfo = cartInfoMapper.selectOne(queryWrapper);
        //如果有，更新数量
        if (!ObjectUtils.isEmpty(dbCartInfo)) {
            //加上新增的商品数量
            dbCartInfo.setSkuNum(dbCartInfo.getSkuNum() + skuNum);
            //若价格发生变化，更新购物车商品价格   给Redis存储用的....
            dbCartInfo.setRealTimePrice(productFeignClient.getSkuPrice(skuId));
            //更新数据库
            cartInfoMapper.update(dbCartInfo, queryWrapper);
        } else {
            CartInfo cartInfo = new CartInfo();
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setSkuId(skuId);
            cartInfo.setCartPrice(skuInfo.getPrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setImgUrl(skuInfo.getSkuDefaultImg());
            cartInfo.setSkuName(skuInfo.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setRealTimePrice(productFeignClient.getSkuPrice(skuId));
            //异步插入数据库
            asyncCartInfoService.saveCartInfo(cartInfo);
        }
        //在redis中存储一份购物车的信息(提高查询效率) user:3:cart 24 值(对象)、
        String cartKey = getCartKey(userId);
        //选择Hash存储结构
        redisTemplate.boundHashOps(cartKey).put(skuId.toString(), dbCartInfo);
        //购物车的信息是需要过期时间
        setExpire(cartKey);
    }


    /**
     * 查询购物车
     *
     * @param userId
     * @param userTempId
     * @return
     */
    @Override
    public List<CartInfo> getCartList(String userId, String userTempId) {

        List<CartInfo> cartInfoList = null;
        //1.当用户没有登录的时候
        if (StringUtils.isEmpty(userId)) {
            //无论是否登录都要先查询下数据库，看看购物车内是否有商品
            cartInfoList = getCartInfoListFromDB(userTempId);
        } else {
            //获取临时购物车 userTempId
            List<CartInfo> tempCartInfoList = getCartInfoListFromDB(userTempId);
            if (!CollectionUtils.isEmpty(tempCartInfoList)) {
                cartInfoList = mergeCartList(tempCartInfoList, userId);
                //合并之后删除临时购物车信息
                asyncCartInfoService.deleteCartInfo(userTempId, null);
            } else {
                cartInfoList = getCartInfoListFromDB(userId);
            }
            return cartInfoList;
        }

        return cartInfoList;
    }


    @Override
    public void checkCart(String userId, Long skuId, Integer isChecked) {
        //1.在数据库里面需要修改选中或者未选中状态
        asyncCartInfoService.checkCart(userId, skuId, isChecked);
        //2.操作redis的选中状态
        String cartKey = getCartKey(userId);
        BoundHashOperations cartOperations = redisTemplate.boundHashOps(cartKey);
        {
            //从Redis中拿取购物车信息
            CartInfo cartInfo = (CartInfo) cartOperations.get(skuId.toString());
            cartInfo.setIsChecked(isChecked);
            //更新Redis中的缓存信息
            cartOperations.put(skuId.toString(), cartInfo);
            //设置过期时间
            setExpire(cartKey);
        }

    }

    /**
     * 删除购物车商品
     *
     * @param userId
     * @param skuId
     */
    @Override
    public void deleteCart(String userId, Long skuId) {
        //在数据库里面删除购物车信息
        asyncCartInfoService.deleteCartInfo(userId, skuId);
        //操作redis删除里面的对应商品
        String cartKey = getCartKey(userId);
        BoundHashOperations boundHashOperations = redisTemplate.boundHashOps(cartKey);
        if (boundHashOperations.hasKey(skuId.toString())) {
            //删除数据
            boundHashOperations.delete(skuId.toString());
        }
    }

    /**
     * 获取所有被选中都商品
     *
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getAllCheckedCartInfo(String userId) {
        String cartKey = getCartKey(userId);
        //拿到该用户所对应的所有购物车信息
        List<CartInfo> cartInfoList = redisTemplate.opsForHash().values(cartKey);
        //通过filter对流进行筛选
        return cartInfoList.stream().filter(cartInfo -> cartInfo.getIsChecked() == 1).collect(Collectors.toList());
    }


    /**
     * 一键清空购物车
     *
     * @param userId
     */
    @Override
    public void clearCart(String userId) {
        //1.从数据库中删除
        asyncCartInfoService.deleteCartInfo(userId, null);
        //2.从redis中删除
        String cartKey = getCartKey(userId);
        redisTemplate.delete(cartKey);
    }


    /**
     * 从数据库中查询缓存信息并更新到缓存
     *
     * @param userId
     * @return
     */
    @Override
    public List<CartInfo> getCartInfoListFromDB(String userId) {
        LambdaQueryWrapper<CartInfo> wrapper = wrapperSupplier.get();
        wrapper.eq(CartInfo::getUserId, userId);
        List<CartInfo> cartInfoList = cartInfoMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            updateCartCache(cartInfoList, userId);
        }
        return cartInfoList;
    }


    /**
     * 设置cartkey
     *
     * @param userId
     * @return
     */
    private String getCartKey(String userId) {
        return RedisConst.USER_KEY_PREFIX + userId + RedisConst.USER_CART_KEY_SUFFIX;
    }

    /**
     * 设置失效时间
     *
     * @param cartKey
     */
    private void setExpire(String cartKey) {
        redisTemplate.expire(cartKey, RedisConst.USER_CART_EXPIRE, TimeUnit.SECONDS);
    }

    /**
     * 合并购物车
     *
     * @param tempCartInfoList
     * @param userId
     * @return
     */
    private List<CartInfo> mergeCartList(List<CartInfo> tempCartInfoList, String userId) {
        //先查出来数据库中购物车的数据
        List<CartInfo> cartInfoListFromDB = getCartInfoListFromDB(userId);
        //将新提交的cartList与数据库中原有的进行对比，这里有点享元模式的味儿
        Map<Long, CartInfo> dbCartInfoMap = cartInfoListFromDB.stream().collect(Collectors.toMap(CartInfo::getSkuId, cartInfo -> cartInfo));
        tempCartInfoList.stream().forEach(tempCartInfo -> {
            Long skuId = tempCartInfo.getSkuId();
            //有则合并
            if (dbCartInfoMap.containsKey(skuId)) {
                CartInfo dbCartInfo = dbCartInfoMap.get(skuId);
                //数据库中的总数量+临时的总数量
                dbCartInfo.setSkuNum(dbCartInfo.getSkuNum() + tempCartInfo.getSkuNum());
                //临时勾选，合并后也勾选
                if (tempCartInfo.getIsChecked() == 1) {
                    dbCartInfo.setIsChecked(1);
                }
                cartInfoMapper.updateById(dbCartInfo);
            } else {
                //把临时用户id改为已登录用户id
                tempCartInfo.setUserId(userId);
                cartInfoMapper.updateById(tempCartInfo);
            }
        });
        return getCartInfoListFromDB(userId);

    }

    /**
     * 更新缓存
     *
     * @param cartInfoList
     * @param userId
     */
    private void updateCartCache(List<CartInfo> cartInfoList, String userId) {
        Map<String, CartInfo> cartInfoMap = cartInfoList.stream().map(cartInfo -> {
            cartInfo.setRealTimePrice(productFeignClient.getSkuPrice(cartInfo.getSkuId()));
            return cartInfo;
        }).collect(Collectors.toMap(cartInfo -> cartInfo.getSkuId().toString(), cartInfo -> cartInfo));
        String cartKey = getCartKey(userId);
        redisTemplate.boundHashOps(cartKey).putAll(cartInfoMap);
        setExpire(cartKey);
    }


    class AsyncCartInfoServiceImpl implements AsyncCartInfoService {


        /**
         * 异步更新
         *
         * @param cartInfo
         */
        @Async
        @Override
        public void updateCartInfo(CartInfo cartInfo) {
            cartInfoMapper.updateById(cartInfo);
        }

        /**
         * 异步保存
         *
         * @param cartInfo
         */
        @Async
        @Override
        public void saveCartInfo(CartInfo cartInfo) {
            cartInfoMapper.insert(cartInfo);
        }

        /**
         * 异步删除
         *
         * @param userId
         * @param skuId
         */
        @Async
        @Override
        public void deleteCartInfo(String userId, Long skuId) {
            //删除数据库里面的内容
            LambdaQueryWrapper<CartInfo> wrapper = wrapperSupplier.get();
            if (!StringUtils.isEmpty(userId)) {
                wrapper.eq(CartInfo::getUserId, userId);
            }
            if (!StringUtils.isEmpty(skuId)) {
                wrapper.eq(CartInfo::getSkuId, skuId);
            }
            cartInfoMapper.delete(wrapper);
        }

        /**
         * 异步修改勾选
         *
         * @param userId
         * @param skuId
         * @param isChecked
         */
        @Async
        @Override
        public void checkCart(String userId, Long skuId, Integer isChecked) {
            //根据条件去修改
            CartInfo cartInfo = new CartInfo();
            cartInfo.setIsChecked(isChecked);
            LambdaQueryWrapper<CartInfo> wrapper = wrapperSupplier.get();
            wrapper.eq(CartInfo::getUserId, userId);
            wrapper.eq(CartInfo::getSkuId, skuId);
            cartInfoMapper.update(cartInfo, wrapper);

        }
    }
}
