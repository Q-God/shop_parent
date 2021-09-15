package com.gmall.product.client;

import com.gmall.entity.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(value = "shop-product", fallback = ProductFeignFallBack.class)
public interface ProductFeignClient {

    //1.根据skuid查询sku信息
    @GetMapping("/sku/getSkuInfo/{skuId}")
    SkuInfo getSkuInfo(@PathVariable Long skuId);

    //2.根据productId,skuId查询商品销售属性key与value
    @GetMapping("/sku/getSkuSalePropertyKeyAndValue/{productId}/{skuId}")
    List<ProductSalePropertyKey> getSkuSalePropertyKeyAndValue(
            @PathVariable Long productId,
            @PathVariable Long skuId
    );

    //3.获取sku的分类信息
    @GetMapping("/sku/getCategoryView/{category3Id}")
    BaseCategoryView getCategoryViewByCategory3Id(@PathVariable Long category3Id);

    //4.根据skuId 查询商品的价格
    @GetMapping("/sku/getSkuPrice/{skuId}")
    BigDecimal getSkuPrice(@PathVariable Long skuId);

    //5.通过SpuId获取相应的Json字符串
    @GetMapping("/sku/getSkuSalePropertyValueId/{productId}")
    Map getSkuSalePropertyValueId(@PathVariable Long productId);

    //6.查询分类
    @GetMapping("/product/getCategoryList")
    List getCategoryList();

    //7.获取品牌信息
    @GetMapping("/sku/getBrandById/{brandId}")
    BaseBrand getBrandById(@PathVariable Long brandId);

    //通过Skuid获取平台属性
    @GetMapping("/product/getPlatformPropertyBySkuId/{skuId}")
    List<PlatformPropertyKey> getPlatformPropertyBySkuId(@PathVariable Long skuId);


}
