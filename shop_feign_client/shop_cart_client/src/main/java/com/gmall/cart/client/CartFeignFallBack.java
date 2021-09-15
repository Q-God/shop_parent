package com.gmall.cart.client;

import com.gmall.entity.CartInfo;
import com.gmall.util.common.result.RetVal;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version v1.0
 * @ClassName CartFeignFallBack
 * @Description TODO
 * @Author Q
 */
@Component
public class CartFeignFallBack implements CartFeignClient {

    @Override
    public RetVal addToCart(Long skuId, Integer skuNum) {
        return null;
    }

    @Override
    public RetVal getCartList() {
        return null;
    }

    @Override
    public RetVal checkCart(Long skuId, Integer isChecked) {
        return null;
    }

    @Override
    public RetVal deleteCart(Long skuId) {
        return null;
    }

    @Override
    public RetVal clearCart() {
        return null;
    }

    @Override
    public List<CartInfo> getCheckedCart(String userId) {
        return null;
    }

    @Override
    public List<CartInfo> queryFromDbToRedis(String userId) {
        return null;
    }
}
