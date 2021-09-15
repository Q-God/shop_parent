package com.gmall.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gmall.entity.*;
import com.gmall.product.client.ProductFeignClient;
import com.gmall.search.*;
import com.gmall.search.service.SearchService;
import lombok.SneakyThrows;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ClassName SearchServiceImpl
 * @Description TODO
 * @Author Q
 */
@Service
public class SearchServiceImpl implements SearchService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SearchServiceImpl.class);
    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ElasticsearchRepository elasticsearchRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 上架
     *
     * @param skuId
     */
    @Override
    public void onSale(Long skuId) {
        Product product = new Product();

        SkuInfo skuInfo = productFeignClient.getSkuInfo(skuId);

        if (!ObjectUtils.isEmpty(skuInfo)) {
            product.setId(skuInfo.getId());
            product.setProductName(skuInfo.getSkuName());
            product.setCreateTime(new Date());
            product.setPrice(skuInfo.getPrice().doubleValue());
            product.setDefaultImage(skuInfo.getSkuDefaultImg());
            BaseBrand brand = productFeignClient.getBrandById(skuInfo.getBrandId());
            if (!ObjectUtils.isEmpty(brand)) {
                product.setBrandName(brand.getBrandName());
                product.setBrandId(brand.getId());
                product.setBrandLogoUrl(brand.getBrandLogoUrl());
            }
            BaseCategoryView categoryView = productFeignClient.getCategoryViewByCategory3Id(skuInfo.getCategory3Id());
            if (!ObjectUtils.isEmpty(categoryView)) {
                product.setCategory1Id(categoryView.getCategory1Id());
                product.setCategory1Name(categoryView.getCategory1Name());
                product.setCategory2Id(categoryView.getCategory2Id());
                product.setCategory2Name(categoryView.getCategory2Name());
                product.setCategory3Id(categoryView.getCategory3Id());
                product.setCategory3Name(categoryView.getCategory3Name());
            }
        }

        List<PlatformPropertyKey> platformPropertyKeyList = productFeignClient.getPlatformPropertyBySkuId(skuId);
        if (!CollectionUtils.isEmpty(platformPropertyKeyList)) {
            List<SearchPlatformProperty> searchPlatformPropertyList = platformPropertyKeyList.stream().map(platformPropertyKey -> {
                SearchPlatformProperty searchPlatformProperty = new SearchPlatformProperty();
                searchPlatformProperty.setPropertyKey(platformPropertyKey.getPropertyKey());
                searchPlatformProperty.setPropertyKeyId(platformPropertyKey.getId());
                List<PlatformPropertyValue> propertyValueList = platformPropertyKey.getPropertyValueList();
                searchPlatformProperty.setPropertyValue(propertyValueList.get(0).getPropertyValue());
                return searchPlatformProperty;
            }).collect(Collectors.toList());

            product.setPlatformProperty(searchPlatformPropertyList);

            elasticsearchRepository.save(product);
        }
    }

    /**
     * 下架
     *
     * @param skuId
     */
    @Override
    public void offSale(Long skuId) {
        elasticsearchRepository.deleteById(skuId);
    }

    /**
     * 热度排名 zset
     *
     * @param skuId
     */
    // @ShopCacheable("hotScore")
    @Override
    public void incrHotScore(Long skuId) {
        //定义一个缓存key
        String hotKey = "hotSocre";

        Double count = redisTemplate.opsForZSet().incrementScore(hotKey, "skuid:" + skuId, 1);

        if (count % 10 == 0) {
            Product product = (Product) elasticsearchRepository.findById(skuId).get();
            product.setHotScore(Math.round(count));

            elasticsearchRepository.save(product);
        }

    }

    @Override
    @SneakyThrows
    public SearchResponseVo searchProduct(SearchParam searchParam) {
        //1.生成动态dsl查询语句
        SearchRequest request = generateQueryDSL(searchParam);
        //2.通过该语句实现查询
        SearchResponse searchResponse = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        //3.将查询到的结果封装到SearchResponseVo里面
        SearchResponseVo searchResponseVo = parseSearchReponse(searchResponse);
        //4.其他返回参数的赋值
        searchResponseVo.setPageNo(searchParam.getPageNo());
        searchResponseVo.setPageSize(searchParam.getPageSize());
        //5.设置总页数
        long totalPages = searchResponseVo.getTotal() / searchResponseVo.getPageSize();
        if (searchResponseVo.getTotal() % searchResponseVo.getPageSize() == 0) {
            searchResponseVo.setTotalPages(totalPages);
        } else {
            searchResponseVo.setTotalPages(totalPages + 1);
        }
        return searchResponseVo;
    }

    /**
     * 动态生成DSL语句
     *
     * @param searchParam
     * @return
     */
    private SearchRequest generateQueryDSL(SearchParam searchParam) {

        BoolQueryBuilder firstBoolQueryBuilder = QueryBuilders.boolQuery();

        Long category1Id = searchParam.getCategory1Id();
        if (!StringUtils.isEmpty(category1Id)) {
            TermQueryBuilder category1IdTerm = QueryBuilders.termQuery("category1Id", category1Id);
            firstBoolQueryBuilder.filter(category1IdTerm);
        }

        Long category2Id = searchParam.getCategory2Id();
        if (!StringUtils.isEmpty(category2Id)) {
            TermQueryBuilder category2IdTerm = QueryBuilders.termQuery("category2Id", category2Id);
            firstBoolQueryBuilder.filter(category2IdTerm);
        }

        Long category3Id = searchParam.getCategory3Id();
        if (!StringUtils.isEmpty(category3Id)) {
            TermQueryBuilder category3IdTerm = QueryBuilders.termQuery("category3Id", category3Id);
            firstBoolQueryBuilder.filter(category3IdTerm);
        }
        //3.构造品牌过滤条件 参数格式 brandName=1:苹果
        String brandName = searchParam.getBrandName();
        if (!StringUtils.isEmpty(brandName)) {
            String[] brandNameParams = brandName.split(":");
            TermQueryBuilder brandNameTerm = QueryBuilders.termQuery("brandId", brandNameParams[0]);
            firstBoolQueryBuilder.filter(brandNameTerm);
        }
        //4.构造平台属性过滤条件  参数格式 &props=4:苹果A14:CPU型号&props=5:5.0英寸以下:屏幕尺寸
        String[] props = searchParam.getProps();
        if (!ObjectUtils.isEmpty(props) && props.length > 0) {
            Arrays.stream(props).forEach(prop -> {
                String[] propParam = prop.split(":");
                if (propParam.length == 3) {
                    //构建父boolQueryBuilder
                    BoolQueryBuilder parentBoolQueryBuilder = QueryBuilders.boolQuery();
                    //构建子boolQueryBuilder
                    BoolQueryBuilder childBoolQueryBuilder = QueryBuilders.boolQuery();
                    childBoolQueryBuilder.must(QueryBuilders.termQuery("platformProperty.propertyKeyId", propParam[0]));
                    childBoolQueryBuilder.must(QueryBuilders.termQuery("platformProperty.propertyValue", propParam[1]));
                    parentBoolQueryBuilder.must(QueryBuilders.nestedQuery("platformProperty", childBoolQueryBuilder, ScoreMode.None));
                    firstBoolQueryBuilder.filter(parentBoolQueryBuilder);
                }
            });
        }

        //5.构造商品名称搜索关键字查询 参数格式 keyword=三星
        String keyword = searchParam.getKeyword();
        if (!StringUtils.isEmpty(keyword)) {
            MatchQueryBuilder productNameMatch = QueryBuilders.matchQuery("productName", keyword).operator(Operator.AND);
            firstBoolQueryBuilder.must(productNameMatch);
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(firstBoolQueryBuilder);

        //分页
        Integer pageNo = searchParam.getPageNo();
        Integer pageSize = searchParam.getPageSize();
        int currentPage = (pageNo - 1) * pageSize;
        sourceBuilder.from(currentPage);
        sourceBuilder.size(pageSize);
        /**
         * 8.设置排序  参数格式 order=2:desc
         * 参数 1---综合(hotScore) 2----价格(price)
         */
        String order = searchParam.getOrder();
        if (!StringUtils.isEmpty(order)) {
            String[] orderParam = order.split(":");
            if (orderParam.length == 2) {
                String filedName = null;
                switch (orderParam[0]) {
                    case "1":
                        filedName = "hotScore";
                        break;
                    case "2":
                        filedName = "price";
                        break;
                    case "3":
                        filedName = "createTime";
                        break;
                    default:
                        filedName = "hostScore";
                }
                sourceBuilder.sort(filedName, orderParam[1].equalsIgnoreCase("asc") ? SortOrder.ASC : SortOrder.DESC);
            } else {
                sourceBuilder.sort("hotScore", SortOrder.DESC);
            }
        }

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("productName");
        highlightBuilder.preTags("<span style = 'color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);


        /**
         * Agg
         */
        sourceBuilder.aggregation(AggregationBuilders.terms("brandIdAgg").field("brandId")
                .subAggregation(AggregationBuilders.terms("brandNameAgg").field("brandName"))
                .subAggregation(AggregationBuilders.terms("brandLogoUrlAgg").field("brandLogoUrl")));

        /**
         * 平台属性Aggs
         */
        sourceBuilder.aggregation(AggregationBuilders.nested("platformPropertyAgg", "platformProperty").subAggregation(AggregationBuilders.terms("propertyKeyIdAgg").field("platformProperty.propertyKeyId")
                .subAggregation(AggregationBuilders.terms("propertyKeyAgg").field("platformProperty.propertyKey"))
                .subAggregation(AggregationBuilders.terms("propertyValueAgg").field("platformProperty.propertyValue"))));

        sourceBuilder.fetchSource(new String[]{"id", "defaultImage", "productName", "price"}, null);

        SearchRequest request = new SearchRequest("product");
        request.types("info");
        request.source(sourceBuilder);
        log.info("拼接好的DSL语句: {}", sourceBuilder.toString());
        return request;
    }

    private SearchResponseVo parseSearchReponse(SearchResponse searchResponse) {
        SearchResponseVo searchResponseVo = new SearchResponseVo();
        //1.拿到商品的基本信息
        SearchHits parentHits = searchResponse.getHits();
        SearchHit[] childHits = parentHits.getHits();
        List<Product> productList = new ArrayList<>();
        if (childHits != null && childHits.length > 0) {
            Arrays.stream(childHits).forEach(hits -> {
                Product product = JSONObject.parseObject(hits.getSourceAsString(), Product.class);
                HighlightField productNameHighlight = hits.getHighlightFields().get("productName");
                if (!ObjectUtils.isEmpty(productNameHighlight)) {
                    Text productNameFragmentHighlight = productNameHighlight.getFragments()[0];
                    product.setProductName(productNameFragmentHighlight.toString());
                }
                productList.add(product);
            });
        }
        searchResponseVo.setProductList(productList);
        searchResponseVo.setTotal(parentHits.getTotalHits());
        //2.拿到品牌的信息
        ParsedLongTerms brandIdAgg = searchResponse.getAggregations().get("brandIdAgg");
        List<SearchBrandVo> searchBrandVoList = brandIdAgg.getBuckets().stream().map(bucket -> {
            SearchBrandVo searchBrandVo = new SearchBrandVo();
            //a.拿取品牌的id
            String brandId = bucket.getKeyAsString();
            searchBrandVo.setBrandId(Long.valueOf(brandId));
            //b.拿取品牌的name
            ParsedStringTerms brandNameAgg = bucket.getAggregations().get("brandNameAgg");
            String brandName = brandNameAgg.getBuckets().get(0).getKeyAsString();
            searchBrandVo.setBrandName(brandName);
            //c.拿取品牌的logo
            ParsedStringTerms brandLogoUrlAgg = bucket.getAggregations().get("brandLogoUrlAgg");
            String brandLogoUrl = brandLogoUrlAgg.getBuckets().get(0).getKeyAsString();
            searchBrandVo.setBrandLogoUrl(brandLogoUrl);
            return searchBrandVo;
        }).collect(Collectors.toList());
        searchResponseVo.setBrandVoList(searchBrandVoList);

        ParsedNested platformPropertyAgg = searchResponse.getAggregations().get("platformPropertyAgg");
        ParsedLongTerms propertyKeyIdAgg = platformPropertyAgg.getAggregations().get("propertyKeyIdAgg");
        if (!CollectionUtils.isEmpty(propertyKeyIdAgg.getBuckets())) {
            List<SearchPlatformPropertyVo> searchPlatformPropertyVoList = propertyKeyIdAgg.getBuckets().stream().map(bucket ->
                    {
                        SearchPlatformPropertyVo searchPlatformPropertyVo = new SearchPlatformPropertyVo();
                        //a.平台属性Id
                        Number propertyKeyId = bucket.getKeyAsNumber();
                        searchPlatformPropertyVo.setPropertyKeyId(propertyKeyId.longValue());

                        //b.平台属性名称
                        ParsedStringTerms propertyKeyAgg = bucket.getAggregations().get("propertyKeyAgg");
                        String propertyKey = propertyKeyAgg.getBuckets().get(0).getKeyAsString();
                        searchPlatformPropertyVo.setPropertyKey(propertyKey);
                        //c.平台属性值的集合
                        ParsedStringTerms propertyValueAgg = bucket.getAggregations().get("propertyValueAgg");
                        List<String> propertyValueList = propertyValueAgg.getBuckets().stream().map(Terms.Bucket::getKeyAsString).collect(Collectors.toList());
                        searchPlatformPropertyVo.setPropertyValueList(propertyValueList);

                        return searchPlatformPropertyVo;
                    }
            ).collect(Collectors.toList());
            searchResponseVo.setPlatformPropertyList(searchPlatformPropertyVoList);
        }


        return searchResponseVo;
    }
}
