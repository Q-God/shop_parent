package com.gmall.cart.client;

import com.gmall.entity.CartInfo;
import com.gmall.util.common.result.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(value = "shop-cart", fallback = CartFeignFallBack.class)
public interface CartFeignClient {

    @PostMapping("/cart/addToCart/{skuId}/{skuNum}")
    RetVal addToCart(@PathVariable Long skuId, @PathVariable Integer skuNum);

    @GetMapping("/cart/getCartList")
    RetVal getCartList();

    @GetMapping("/cart/checkCart/{skuId}/{isChecked}")
    RetVal checkCart(@PathVariable Long skuId, @PathVariable Integer isChecked);

    @DeleteMapping("/cart/deleteCart/{skuId}")
    RetVal deleteCart(@PathVariable Long skuId);

    @DeleteMapping("/cart/clearCart")
    RetVal clearCart();

    @GetMapping("/cart/getCheckedCart/{userId}")
    List<CartInfo> getCheckedCart(@PathVariable String userId);

    @GetMapping("/cart/queryFromDbToRedis/{userId}")
    List<CartInfo> queryFromDbToRedis(@PathVariable String userId);
}
