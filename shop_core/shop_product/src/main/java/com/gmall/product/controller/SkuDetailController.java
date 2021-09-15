package com.gmall.product.controller;

import com.gmall.entity.BaseBrand;
import com.gmall.entity.BaseCategoryView;
import com.gmall.entity.ProductSalePropertyKey;
import com.gmall.entity.SkuInfo;
import com.gmall.product.service.BaseBrandService;
import com.gmall.product.service.SkuDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName ProductApiController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/sku/")
public class SkuDetailController {

    @Autowired
    private SkuDetailService skuDetailService;

    @Autowired
    private BaseBrandService baseBrandService;

    //1.根据skuid查询sku信息
    @GetMapping("/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        return skuDetailService.getSkuInfo(skuId);
    }

    //2.根据productId,skuId查询商品销售属性key与value
    @GetMapping("getSkuSalePropertyKeyAndValue/{productId}/{skuId}")
    public List<ProductSalePropertyKey> getSkuSalePropertyKeyAndValue(
            @PathVariable Long productId,
            @PathVariable Long skuId
    ) {
        return skuDetailService.getSkuSalePropertyKeyAndValue(productId, skuId);
    }

    //3.获取sku的分类信息
    @GetMapping("getCategoryView/{category3Id}")
    public BaseCategoryView getCategoryViewByCategory3Id(@PathVariable Long category3Id) {
        return skuDetailService.getBaseCategoryViewByCategory3Id(category3Id);
    }

    //4.根据skuId 查询商品的价格
    @GetMapping("/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable Long skuId) {
        return skuDetailService.getSkuPrice(skuId);
    }

    //5.通过SpuId获取相应的Json字符串
    @GetMapping("/getSkuSalePropertyValueId/{productId}")
    public Map getSkuSalePropertyValueId(@PathVariable Long productId) {
        return skuDetailService.getSkuSalePropertyValueId(productId);
    }


    //6.通过brandId获取品牌信息
    @GetMapping("/getBrandById/{brandId}")
    public BaseBrand getBrandById(@PathVariable("brandId") Long brandId) {
        return baseBrandService.getBaseBrandById(brandId);
    }
}
