package com.gmall.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmall.entity.BaseCategoryView;
import com.gmall.entity.ProductSalePropertyKey;
import com.gmall.entity.SkuInfo;
import com.gmall.product.client.ProductFeignClient;
import com.gmall.search.client.SearchFeignClient;
import com.gmall.util.web.handler.MyExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

/**
 * @version v1.0
 * @ClassName WebSkuDetailController
 * @Description TODO
 * @Author Q
 */
@Controller
public class WebSkuDetailController {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SearchFeignClient searchFeignClient;

    private final Supplier<Map<String, Object>> mapSupplier = HashMap::new;

    //编写访问的控制器！
    @GetMapping("/{skuId}.html")
    public String getSkuDetail(@PathVariable Long skuId, Model model) throws JsonProcessingException {

        //将结果统一封装到Map中去
        Map<String, Object> map = mapSupplier.get();

        CompletableFuture<SkuInfo> skuInfoFuture = CompletableFuture.supplyAsync(() -> {
            SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
            map.put("skuInfo", skuInfo);
            return skuInfo;
        }, MyExecutor.getInstance());

        CompletableFuture<Void> spuSalePropertyListFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            // 通过ProductId,SkuId获取销售属性，销售属性值，并锁定的数据
            List<ProductSalePropertyKey> skuSalePropertyKeyAndValueList = productFeignClient.getSkuSalePropertyKeyAndValue(skuInfo.getProductId(), skuId);
            map.put("spuSalePropertyList", skuSalePropertyKeyAndValueList);
        });

        CompletableFuture<Void> categoryViewFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            // 根据三级分类Id 来获取分类数据
            BaseCategoryView categoryView = productFeignClient.getCategoryViewByCategory3Id(skuInfo.getCategory3Id());
            map.put("categoryView", categoryView);
        });

        CompletableFuture<Void> salePropertyValueIdJsonFuture = skuInfoFuture.thenAcceptAsync(skuInfo -> {
            // 根据spuId 获取销售属性值和skuId 组成的Map 集合数据
            Map skuSalePropertyValueMap = productFeignClient.getSkuSalePropertyValueId(skuInfo.getProductId());
            try {
                map.put("salePropertyValueIdJson", new ObjectMapper().writeValueAsString(skuSalePropertyValueMap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> priceFuture = CompletableFuture.runAsync(() -> {
            //获取价格
            BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
            map.put("price", skuPrice);
        });

        CompletableFuture<Void> incrHotScoreFuture = CompletableFuture.runAsync(() ->
                searchFeignClient.incrHotScore(skuId), MyExecutor.getInstance()
        );

        CompletableFuture.allOf(skuInfoFuture, spuSalePropertyListFuture, categoryViewFuture, salePropertyValueIdJsonFuture, priceFuture, incrHotScoreFuture).join();

        // // 通过skuId 获取skuInfo 数据
        // SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);
        // // 通过ProductId,SkuId获取销售属性，销售属性值，并锁定的数据
        // List<ProductSalePropertyKey> skuSalePropertyKeyAndValueList = productFeignClient.getSkuSalePropertyKeyAndValue(skuInfo.getProductId(), skuId);
        // //获取价格
        // BigDecimal skuPrice = productFeignClient.getSkuPrice(skuId);
        // // 根据三级分类Id 来获取分类数据
        // BaseCategoryView categoryView = productFeignClient.getCategoryViewByCategory3Id(skuInfo.getCategory3Id());
        // // 根据spuId 获取销售属性值和skuId 组成的Map 集合数据
        // Map skuSalePropertyValueMap = productFeignClient.getSkuSalePropertyValueId(skuInfo.getProductId());
        // map.put("skuInfo", skuInfo);
        // map.put("categoryView", categoryView);
        // map.put("spuSalePropertyList", skuSalePropertyKeyAndValueList);
        // map.put("price", skuPrice);
        // map.put("salePropertyValueIdJson", new ObjectMapper().writeValueAsString(skuSalePropertyValueMap));
        model.addAllAttributes(map);
        return "detail/index";
    }
}
