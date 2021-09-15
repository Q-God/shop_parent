package com.gmall.product.service;

import com.gmall.entity.BaseCategoryView;
import com.gmall.entity.ProductSalePropertyKey;
import com.gmall.entity.SkuInfo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface SkuDetailService {

    /**
     * 根据skuId查询skuInfo
     *
     * @param skuId
     * @return
     */
    SkuInfo getSkuInfo(Long skuId);

    /**
     * 通过三级Id查询分类信息
     *
     * @param category3Id
     * @return
     */
    BaseCategoryView getBaseCategoryViewByCategory3Id(Long category3Id);

    /**
     * 获取Sku价格 BigDecimal防止失精度
     *
     * @param skuId
     * @return
     */
    BigDecimal getSkuPrice(Long skuId);

    /**
     * 通过SpuId获取相应的Json字符串
     *
     * @param productId
     * @return
     */
    Map getSkuSalePropertyValueId(Long productId);

    /**
     * 根据productId,skuId查询商品销售属性key与value
     *
     * @param productId
     * @param skuId
     * @return
     */
    List<ProductSalePropertyKey> getSkuSalePropertyKeyAndValue(Long productId, Long skuId);
}
