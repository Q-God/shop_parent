package com.gmall.web.controller;

import com.gmall.cart.client.CartFeignClient;
import com.gmall.entity.SkuInfo;
import com.gmall.product.client.ProductFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @version v1.0
 * @ClassName WebCartController
 * @Description TODO
 * @Author Q
 */
@Controller
public class WebCartController {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @GetMapping("/addCart.html")
    public String addCart(@RequestParam Long skuId, @RequestParam(required = false) Integer skuNum, HttpServletRequest request) {
        cartFeignClient.addToCart(skuId, skuNum);
        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        if (skuInfo != null) {
            request.setAttribute("skuInfo", skuInfo);
            request.setAttribute("skuNum", skuNum);

        }

        return "cart/addCart";
    }

    @GetMapping("cart.html")
    public String cartList(HttpServletRequest request) {
        return "cart/index";
    }
}
