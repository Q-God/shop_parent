package com.gmall.search.client;

import com.gmall.search.SearchParam;
import com.gmall.util.common.result.RetVal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @version v1.0
 * @ClassName client
 * @Description TODO
 * @Author Q
 */
@FeignClient(value = "shop-search", fallback = SearchFeignFallBack.class)
public interface SearchFeignClient {
    @GetMapping("/search/onSale/{skuId}")
    RetVal onSale(@PathVariable Long skuId);


    @GetMapping("/search/offSale/{skuId}")
    RetVal offSale(@PathVariable Long skuId);

    @GetMapping("/search/incrHotScore/{skuId}")
    RetVal incrHotScore(@PathVariable Long skuId);

    //5.商品搜索
    @PostMapping("/search")
    RetVal searchProduct(@RequestBody SearchParam searchParam);
}
