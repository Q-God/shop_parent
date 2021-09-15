package com.gmall.cart.service;

import com.gmall.entity.CartInfo;

import java.util.List;

/**
 * @version v1.0
 * @ClassName CartInfoService
 * @Description TODO
 * @Author Q
 */
public interface CartInfoService {

    /**
     * 添加到购物车
     *
     * @param userId
     * @param skuId
     * @param skuNum
     */
    void addToCart(String userId, Long skuId, Integer skuNum);

    /**
     * 查询购物车
     *
     * @param userId
     * @param userTempId
     * @return
     */
    List<CartInfo> getCartList(String userId, String userTempId);

    /**
     * 商品勾选状态
     *
     * @param userId
     * @param skuId
     * @param isChecked
     */
    void checkCart(String userId, Long skuId, Integer isChecked);

    /**
     * 删除购物车商品
     *
     * @param userId
     * @param skuId
     */
    void deleteCart(String userId, Long skuId);

    /**
     * 查询全部已勾选商品
     *
     * @param userId
     * @return
     */
    List<CartInfo> getAllCheckedCartInfo(String userId);

    /**
     * 一键清空购物车
     *
     * @param userId
     */
    void clearCart(String userId);

    /**
     * 从数据库中查询缓存信息并更新到缓存
     *
     * @param userId
     * @return
     */
    List<CartInfo> getCartInfoListFromDB(String userId);

    interface AsyncCartInfoService {
        /**
         * @param cartInfo
         */
        void updateCartInfo(CartInfo cartInfo);

        /**
         * @param cartInfo
         */
        void saveCartInfo(CartInfo cartInfo);

        /**
         * @param userId
         * @param skuId
         */
        void deleteCartInfo(String userId, Long skuId);

        /**
         * @param userId
         * @param skuId
         * @param isChecked
         */
        void checkCart(String userId, Long skuId, Integer isChecked);
    }

}
