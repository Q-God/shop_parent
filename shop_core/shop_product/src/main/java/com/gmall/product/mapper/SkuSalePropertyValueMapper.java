package com.gmall.product.mapper;

import com.gmall.entity.SkuSalePropertyValue;
import com.gmall.mybatisplus_ext.mapper.ExtBaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName SkuSalePropertyValueMapper
 * @Description TODO
 * @Author Q
 */
public interface SkuSalePropertyValueMapper extends ExtBaseMapper<SkuSalePropertyValue> {
    /**
     * @param productId
     * @return
     */
    List<Map> selectSkuSalePropertyValueId(Long productId);
}
