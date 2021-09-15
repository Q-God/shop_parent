package com.gmall.product.client;

import com.gmall.entity.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName ProductFallBack
 * @Description TODO
 * @Author Q
 */
@Component
public class ProductFeignFallBack implements ProductFeignClient {


    @Override
    public SkuInfo getSkuInfo(Long skuId) {
        return null;
    }

    @Override
    public List<ProductSalePropertyKey> getSkuSalePropertyKeyAndValue(Long productId, Long skuId) {
        return null;
    }

    @Override
    public BaseCategoryView getCategoryViewByCategory3Id(Long category3Id) {
        return null;
    }

    @Override
    public BigDecimal getSkuPrice(Long skuId) {
        return new BigDecimal("0");
    }

    @Override
    public Map getSkuSalePropertyValueId(Long productId) {
        return null;
    }

    @Override
    public List getCategoryList() {
        return null;
    }

    @Override
    public BaseBrand getBrandById(Long brandId) {
        return null;
    }

    @Override
    public List<PlatformPropertyKey> getPlatformPropertyBySkuId(Long skuId) {
        return null;
    }
}
