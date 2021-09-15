package com.gmall.search.client;

import com.gmall.search.SearchParam;
import com.gmall.util.common.result.RetVal;
import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @ClassName SearchFeignFallBack
 * @Description TODO
 * @Author Q
 */
@Component
public class SearchFeignFallBack implements SearchFeignClient {
    @Override
    public RetVal onSale(Long skuId) {
        return RetVal.fail();
    }

    @Override
    public RetVal offSale(Long skuId) {
        return RetVal.fail();
    }

    @Override
    public RetVal incrHotScore(Long skuId) {
        return null;
    }

    @Override
    public RetVal searchProduct(SearchParam searchParam) {
        return null;
    }
}
