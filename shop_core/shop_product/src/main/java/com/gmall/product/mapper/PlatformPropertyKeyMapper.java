package com.gmall.product.mapper;

import com.gmall.entity.PlatformPropertyKey;
import com.gmall.mybatisplus_ext.mapper.ExtBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 属性表 Mapper 接口
 * </p>
 *
 * @author Q
 * @since 2021-08-23
 */
public interface PlatformPropertyKeyMapper extends ExtBaseMapper<PlatformPropertyKey> {

    List<PlatformPropertyKey> selectPlatformPropertyByCategoryId(@Param("category1Id") Long category1Id, @Param("category2Id") Long category2Id, @Param("category3Id") Long category3Id);

    List<PlatformPropertyKey> getPlatformPropertyBySkuId(Long skuId);
}
