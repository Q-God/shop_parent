package com.gmall.product.mapper;

import com.gmall.entity.ProductSalePropertyKey;
import com.gmall.mybatisplus_ext.mapper.ExtBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Spu销售属性Mapper
 */
public interface ProductSalePropertyKeyMapper extends ExtBaseMapper<ProductSalePropertyKey> {

    List<ProductSalePropertyKey> selectProductSalePropertyKeyListById(Long productSpuId);

    List<ProductSalePropertyKey> selectSkuSalePropertyKeyAndValue(@Param("productId") Long productId, @Param("skuId") Long skuId);
}
