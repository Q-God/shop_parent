package com.gmall.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.entity.*;
import com.gmall.product.mapper.*;
import com.gmall.product.service.ManageService;
import com.gmall.search.client.SearchFeignClient;
import com.gmall.utils.core.constant.MqConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Q
 * @since 2021-08-21
 */
@Service
public class ManageServiceImpl implements ManageService {

    /**
     * 工具
     */
    //判断对象为空
    private final Predicate<Object> objectIsNull = Objects::isNull;
    //判断对象不为空
    private final Predicate<Object> objectNotNull = Objects::nonNull;
    //判断集合是否为空
    private final Predicate<Collection> collectionIsEmpty = Collection::isEmpty;


    @Autowired
    private BaseCategory1Mapper baseCategory1Mapper;

    @Autowired
    private BaseCategory2Mapper baseCategory2Mapper;

    @Autowired
    private BaseCategory3Mapper baseCategory3Mapper;

    @Autowired
    private BaseCategoryViewMapper baseCategoryViewMapper;

    @Autowired
    private PlatformPropertyKeyMapper platformPropertyKeyMapper;

    @Autowired
    private PlatformPropertyValueMapper platformPropertyValueMapper;

    @Autowired
    private ProductSpuMapper productSpuMapper;

    @Autowired
    private BaseSalePropertyMapper baseSalePropertyMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private ProductSalePropertyKeyMapper productSalePropertyKeyMapper;

    @Autowired
    private ProductSalePropertyValueMapper productSalePropertyValueMapper;

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;

    @Autowired
    private SkuPlatformPropertyValueMapper skuPlatformPropertyValueMapper;

    @Autowired
    private SkuSalePropertyValueMapper skuSalePropertyValueMapper;

    @Autowired
    private SearchFeignClient searchFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 查询所有一级分类
     *
     * @return
     */
    @Override
    public List<BaseCategory1> getCategory1() {
        return baseCategory1Mapper.selectList(null);
    }

    /**
     * 根据一级分类Id查询二级分类
     *
     * @param category1Id 一级分类Id
     * @return
     */
    @Override
    public List<BaseCategory2> getCategory2(Long category1Id) {
        QueryWrapper<BaseCategory2> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category1_id", category1Id);
        return baseCategory2Mapper.selectList(queryWrapper);
    }

    /**
     * 根据二级分类Id查询三级分类
     *
     * @param category2Id
     * @return
     */
    @Override
    public List<BaseCategory3> getCategory3(Long category2Id) {
        LambdaQueryWrapper<BaseCategory3> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(BaseCategory3::getCategory2Id, category2Id);
//        QueryWrapper<BaseCategory3> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("category2_id", category2Id);
        return baseCategory3Mapper.selectList(lambdaQueryWrapper);
    }

    /**
     * 查询分类
     *
     * @return
     */
    @Override
    public List<JSONObject> getCategoryList() {
        //定义返回类型
        List<JSONObject> parentCategoryList = new ArrayList<>();
        //从数据可中查出所有分类
        List<BaseCategoryView> baseCategoryViews = baseCategoryViewMapper.selectList(null);
        //设置索引
        AtomicReference<Integer> index = new AtomicReference<>(1);
        //根据Category1Id进行分组
        Map<Long, List<BaseCategoryView>> category1Map = baseCategoryViews.stream().collect(Collectors.groupingBy(BaseCategoryView::getCategory1Id));
        //遍历分组后的1级分类
        category1Map.entrySet().parallelStream().forEach(category1 -> {
            //定义自定义json对象，定义返回数据类型，true为有序
            JSONObject categroy1Json = new JSONObject(true);
            //索引++
            categroy1Json.put("index", index.getAndSet(index.get() + 1));
            //category1Id
            categroy1Json.put("categoryId", category1.getKey());
            //category1Name
            categroy1Json.put("categoryName", category1.getValue().get(0).getCategory1Name());
            //创建category1Children集合
            List<JSONObject> category1Children = new ArrayList<>();
            //根据Category2Id进行分组
            Map<Long, List<BaseCategoryView>> category2Map = category1.getValue().parallelStream().collect(Collectors.groupingBy(BaseCategoryView::getCategory2Id));
            //使用并行流遍历category2Map.entrySet()
            category2Map.entrySet().parallelStream().forEach(category2 -> {
                //定义自定义json对象，定义返回数据类型，true为有序
                JSONObject categroy2Json = new JSONObject(true);
                //category2Id
                categroy2Json.put("categoryId", category2.getKey());
                //category2Name
                categroy2Json.put("categoryName", category2.getValue().get(0).getCategory2Name());
                //创建category2Children集合
                List<JSONObject> category2Children = new ArrayList<>();
                //使用并行流遍历category2List
                category2.getValue().parallelStream().forEach(category3 -> {
                    //定义自定义json对象，定义返回数据类型，true为有序
                    JSONObject categroy3Json = new JSONObject(true);
                    //category3Id
                    categroy3Json.put("categoryId", category3.getCategory3Id());
                    //category3Name
                    categroy3Json.put("categoryName", category3.getCategory3Name());
                    //向category2Children添加categroy3Json的数据
                    category2Children.add(categroy3Json);
                });
                //向categroy2Json添加category2Children
                categroy2Json.put("categoryChild", category2Children);
                //向category1Children添加categroy2Json
                category1Children.add(categroy2Json);
            });
            //向categroy1Json添加category1Children
            categroy1Json.put("categoryChild", category1Children);
            //向parentCategoryList添加categroy1Json
            parentCategoryList.add(categroy1Json);
        });
        return parentCategoryList;
    }

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
    @Override
    public List<PlatformPropertyKey> getPlatformPropertyValueList(Long category1Id, Long category2Id, Long category3Id) {
        return platformPropertyKeyMapper.selectPlatformPropertyByCategoryId(category1Id, category2Id, category3Id);
    }

    /**
     * 保存平台属性方法
     *
     * @param platformPropertyKey
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePlatformPropertyKey(PlatformPropertyKey platformPropertyKey) {
        //什么情况下是添加，什么情况下是更新，根据platformPropertyKey的Id判断
        //更新操作
        if (objectNotNull.test(platformPropertyKey.getId())) {
            platformPropertyKeyMapper.updateById(platformPropertyKey);
        } else {
            //新增操作
            platformPropertyKeyMapper.insert(platformPropertyKey);
        }

        //platformPropertyValue平台属性值
        //修改：通过先删除{platformPropertyValue}，在以新增的方式添加
        //删除条件  platformPropertyValue.getPropertyKeyId() == platformPropertyKey.getId()
        LambdaQueryWrapper<PlatformPropertyValue> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(PlatformPropertyValue::getId, platformPropertyKey.getId());
        platformPropertyValueMapper.delete(queryWrapper);
        //获取页面传递过来的所有平台属性值数据
        List<PlatformPropertyValue> propertyValueList = platformPropertyKey.getPropertyValueList();
        if (!collectionIsEmpty.and(objectIsNull).test(propertyValueList)) {
            //Stream流遍历
            propertyValueList.stream().parallel().forEach(propertyValue -> {
                //获取平台属性Id给propertyValue
                propertyValue.setPropertyKeyId(platformPropertyKey.getId());
                //platformPropertyValueMapper.insert(propertyValue);
            });
            //扩展批处理
            platformPropertyValueMapper.insertBatchSomeColumn(propertyValueList);
        }
    }

    /**
     * 根据platformPropertyKeyId查询平台属性对象
     *
     * @param platformPropertyKeyId
     * @return
     */
    @Override
    public PlatformPropertyKey getPlatformPropertyKey(Long platformPropertyKeyId) {
        PlatformPropertyKey platformPropertyKey = platformPropertyKeyMapper.selectById(platformPropertyKeyId);
        if (objectNotNull.test(platformPropertyKey)) {
            platformPropertyKey.setPropertyValueList(getPlatformPropertyValueList(platformPropertyKeyId));
            return platformPropertyKey;
        }
        return null;

    }

    private List<PlatformPropertyValue> getPlatformPropertyValueList(Long platformPropertyKeyId) {
        LambdaQueryWrapper<PlatformPropertyValue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlatformPropertyValue::getPropertyKeyId, platformPropertyKeyId);
        return platformPropertyValueMapper.selectList(queryWrapper);
    }

    /**
     * spu分页查询
     *
     * @param pageParam
     * @param productSpu
     * @return
     */
    @Override
    public IPage<ProductSpu> getProductSpuPage(Page<ProductSpu> pageParam, ProductSpu productSpu) {
        LambdaQueryWrapper<ProductSpu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductSpu::getCategory3Id, productSpu.getCategory3Id());
        queryWrapper.orderByDesc(ProductSpu::getId);
        return productSpuMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * spu分页查询
     *
     * @param pageParam
     * @param category3Id
     * @return
     */
    @Override
    public IPage<ProductSpu> getProductSpuByPage(Page<ProductSpu> pageParam, Long category3Id) {
        LambdaQueryWrapper<ProductSpu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductSpu::getCategory3Id, category3Id);
        queryWrapper.orderByDesc(ProductSpu::getId);
        return productSpuMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 保存商品数据
     *
     * @param productSpu
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveProductSpu(ProductSpu productSpu) {
        //PropertySpu表
        productSpuMapper.insert(productSpu);
        //ProductImage表
        List<ProductImage> productImageList = productSpu.getProductImageList();
        //临界条件
        if (!collectionIsEmpty.and(objectIsNull).test(productImageList)) {
            productImageList.stream().parallel().forEach(productImage -> {
                productImage.setProductId(productSpu.getId());
                //productImageMapper.insert(productImage);
            });
            productImageMapper.insertBatchSomeColumn(productImageList);
        }
        //ProductSalePropertyKey表
        List<ProductSalePropertyKey> salePropertyKeyList = productSpu.getSalePropertyKeyList();
        //临界条件
        if (!collectionIsEmpty.and(objectIsNull).test(salePropertyKeyList)) {
            salePropertyKeyList.stream().forEach(salePropertyKey -> {
                salePropertyKey.setProductId(productSpu.getId());
                // productSalePropertyKeyMapper.insert(salePropertyKey);

                //ProductSalePropertyValue表
                List<ProductSalePropertyValue> salePropertyValueList = salePropertyKey.getSalePropertyValueList();
                //临界条件
                if (!collectionIsEmpty.and(objectIsNull).test(salePropertyValueList)) {
                    salePropertyValueList.stream().forEach(salePropertyValue -> {
                        salePropertyValue.setProductId(productSpu.getId());
                        salePropertyValue.setSalePropertyKeyName(salePropertyKey.getSalePropertyKeyName());
                        //productSalePropertyValueMapper.insert(salePropertyValue);
                    });
                    productSalePropertyValueMapper.insertBatchSomeColumn(salePropertyValueList);
                }
            });
        }
        productSalePropertyKeyMapper.insertBatchSomeColumn(salePropertyKeyList);
    }

    /**
     * 根据ProductSpuId查询productImageList
     *
     * @param productSpuId
     * @return
     */
    @Override
    public List<ProductImage> getProductImage(Long productSpuId) {
        LambdaQueryWrapper<ProductImage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductImage::getProductId, productSpuId);
        return productImageMapper.selectList(queryWrapper);
    }

    /**
     * 根据PeoductSpuId 查询销售属性集合
     *
     * @param productSpuId
     * @return
     */
    @Override
    public List<ProductSalePropertyKey> getProductSalePropertyKeyList(Long productSpuId) {
        return productSalePropertyKeyMapper.selectProductSalePropertyKeyListById(productSpuId);
    }

    /**
     * 查询所有的销售属性数据
     *
     * @return
     */
    @Override
    public List<BaseSaleProperty> getBaseSalePropertyList() {
        return baseSalePropertyMapper.selectList(null);
    }

    /**
     * 保存Sku
     *
     * @param skuInfo
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {
        /*
        skuInfo 库存单元表
        skuImage 库存图片表
        skuSalePropertyValue表 sku销售属性值表{sku与销售属性值的中间表} --- skuInfo ，skuSalePropertyValue表
        skuPlatformPropertyValue sku与平台属性值的中间表 --- skuInfo  PlatformPropertyValue
         */
        skuInfoMapper.insert(skuInfo);
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        //临界
        if (!collectionIsEmpty.and(objectIsNull).test(skuImageList)) {
            skuImageList.stream().forEach(skuImage -> {
                skuImage.setSkuId(skuInfo.getId());
                //skuImageMapper.insert(skuImage);
            });
            skuImageMapper.insertBatchSomeColumn(skuImageList);
        }

        List<SkuSalePropertyValue> skuSalePropertyValueList = skuInfo.getSkuSalePropertyValueList();
        if (!!collectionIsEmpty.and(objectIsNull).test(skuImageList)) {
            skuSalePropertyValueList.stream().forEach(skuSalePropertyValue -> {
                skuSalePropertyValue.setSkuId(skuInfo.getId());
                skuSalePropertyValue.setProductId(skuInfo.getProductId());
                //skuSalePropertyValueMapper.insert(skuSalePropertyValue);
            });
            skuSalePropertyValueMapper.insertBatchSomeColumn(skuSalePropertyValueList);
        }

        List<SkuPlatformPropertyValue> skuPlatformPropertyValueList = skuInfo.getSkuPlatformPropertyValueList();
        if (!!collectionIsEmpty.and(objectIsNull).test(skuPlatformPropertyValueList)) {
            skuPlatformPropertyValueList.stream().forEach(skuPlatformPropertyValue -> {
                skuPlatformPropertyValue.setSkuId(skuInfo.getId());
                //  skuPlatformPropertyValueMapper.insert(skuPlatformPropertyValue);
            });
            skuPlatformPropertyValueMapper.insertBatchSomeColumn(skuPlatformPropertyValueList);
        }
    }

    /**
     * Sku分页列表
     *
     * @param pageParam
     * @return
     */
    @Override
    public Page<SkuInfo> getSkuInfoPage(Page<SkuInfo> pageParam) {
        LambdaQueryWrapper<SkuInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(SkuInfo::getId);
        return skuInfoMapper.selectPage(pageParam, queryWrapper);
    }

    /**
     * 商品上架
     *
     * @param skuId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void onSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(1);
        skuInfoMapper.updateById(skuInfo);
        rabbitTemplate.convertAndSend(MqConst.ON_OFF_SALE_EXCHANGE, MqConst.ON_SALE_ROUTING_KEY, skuId);
        //searchFeignClient.onSale(skuId);
    }

    /**
     * 商品下架
     *
     * @param skuId
     */
    @Override
    public void offSale(Long skuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        skuInfo.setIsSale(0);
        skuInfoMapper.updateById(skuInfo);
        rabbitTemplate.convertAndSend(MqConst.ON_OFF_SALE_EXCHANGE, MqConst.OFF_SALE_ROUTING_KEY, skuId);
        //searchFeignClient.offSale(skuId);
    }

    /**
     * 根据SkuId获取平台属性
     *
     * @param skuId
     * @return
     */
    @Override
    public List<PlatformPropertyKey> getPlatformPropertyBySkuId(Long skuId) {
        return platformPropertyKeyMapper.getPlatformPropertyBySkuId(skuId);
    }
}
