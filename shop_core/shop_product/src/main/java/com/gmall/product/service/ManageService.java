package com.gmall.product.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.entity.*;

import java.util.List;

/**
 * <p>
 * 三级分类表 服务类
 * </p>
 *
 * @author Q
 * @since 2021-08-21
 */
public interface ManageService {

    /**
     * 查询所有一级分类
     *
     * @return
     */
    List<BaseCategory1> getCategory1();

    /**
     * 根据一级分类Id查询二级分类
     *
     * @param category1Id 一级分类Id
     * @return
     */
    List<BaseCategory2> getCategory2(Long category1Id);

    /**
     * 根据二级分类Id查询三级分类
     *
     * @param category2Id
     * @return
     */
    List<BaseCategory3> getCategory3(Long category2Id);

    /**
     * @return
     */
    List<JSONObject> getCategoryList();

    /**
     * 根据分类Id 获取平台属性数据
     * 接口说明：
     * 1，平台属性可以挂在一级分类、二级分类和三级分类
     * 2，查询一级分类下面的平台属性，传：category1Id，0，0；   取出该分类的平台属性
     * 3，查询二级分类下面的平台属性，传：category1Id，category2Id，0；
     * 取出对应一级分类下面的平台属性与二级分类对应的平台属性
     * 4，查询三级分类下面的平台属性，传：category1Id，category2Id，category3Id；
     * 取出对应一级分类、二级分类与三级分类对应的平台属性
     *
     * @param category1Id
     * @param category2Id
     * @param category3Id
     * @return
     */
    List<PlatformPropertyKey> getPlatformPropertyValueList(Long category1Id, Long category2Id, Long category3Id);

    /**
     * 保存平台属性方法
     *
     * @param platformPropertyKey
     */
    void savePlatformPropertyKey(PlatformPropertyKey platformPropertyKey);

    /**
     * 根据platformPropertyKeyId查询平台属性对象
     *
     * @param platformPropertyKeyId
     * @return
     */
    PlatformPropertyKey getPlatformPropertyKey(Long platformPropertyKeyId);

    /**
     * spu分页查询
     *
     * @param pageParam
     * @param productSpu
     * @return
     */
    IPage<ProductSpu> getProductSpuPage(Page<ProductSpu> pageParam, ProductSpu productSpu);

    /**
     * spu分页查询
     *
     * @param pageParam
     * @param category3Id
     * @return
     */
    IPage<ProductSpu> getProductSpuByPage(Page<ProductSpu> pageParam, Long category3Id);


    /**
     * 保存商品数据
     *
     * @param productSpu
     */
    void saveProductSpu(ProductSpu productSpu);

    /**
     * 根据ProductSpuId查询productImageList
     *
     * @param productSpuId
     * @return
     */
    List<ProductImage> getProductImage(Long productSpuId);

    /**
     * 根据PeoductSpuId 查询销售属性集合
     *
     * @param productSpuId
     * @return
     */
    List<ProductSalePropertyKey> getProductSalePropertyKeyList(Long productSpuId);

    /**
     * 查询所有的销售属性数据
     *
     * @return
     */
    List<BaseSaleProperty> getBaseSalePropertyList();

    /**
     * 保存Sku
     *
     * @param skuInfo
     */
    void saveSkuInfo(SkuInfo skuInfo);

    /**
     * Sku分页列表
     *
     * @param pageParam
     * @return
     */
    Page<SkuInfo> getSkuInfoPage(Page<SkuInfo> pageParam);

    /**
     * 商品上架
     *
     * @param skuId
     */
    void onSale(Long skuId);

    /**
     * 商品下架
     *
     * @param skuId
     */
    void offSale(Long skuId);

    /**
     * 根据SkuId获取平台属性
     *
     * @param skuId
     * @return
     */
    List<PlatformPropertyKey> getPlatformPropertyBySkuId(Long skuId);
}
