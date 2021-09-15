package com.gmall.search.controller;

import com.gmall.search.Product;
import com.gmall.search.SearchParam;
import com.gmall.search.SearchResponseVo;
import com.gmall.search.service.SearchService;
import com.gmall.util.common.result.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @version v1.0
 * @ClassName SearchController
 * @Description TODO
 * @Author Q
 */

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private SearchService searchService;

    @GetMapping("/createIndex")
    public RetVal createIndex() {
        elasticsearchRestTemplate.createIndex(Product.class);
        elasticsearchRestTemplate.putMapping(Product.class);
        return RetVal.ok();
    }

    @GetMapping("/onSale/{skuId}")
    public RetVal onSale(@PathVariable Long skuId) {
        searchService.onSale(skuId);
        return RetVal.ok();
    }

    /**
     *
     * @param skuId
     * @return
     */
    @GetMapping("/offSale/{skuId}")
    public RetVal offSale(@PathVariable Long skuId) {
        searchService.offSale(skuId);
        return RetVal.ok();
    }

    @GetMapping("incrHotScore/{skuId}")
    public RetVal incrHotScore(@PathVariable Long skuId) {
        searchService.incrHotScore(skuId);
        return RetVal.ok();
    }

    //5.商品搜索
    @PostMapping
    public RetVal searchProduct(@RequestBody SearchParam searchParam) {
        SearchResponseVo searchResponseVo = searchService.searchProduct(searchParam);
        return RetVal.ok(searchResponseVo);
    }
}
