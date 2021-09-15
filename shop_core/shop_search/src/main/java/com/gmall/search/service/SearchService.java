package com.gmall.search.service;

import com.gmall.search.SearchParam;
import com.gmall.search.SearchResponseVo;

/**
 * @version v1.0
 * @ClassName SearchService
 * @Description 检索服务
 * @Author Q
 */
public interface SearchService {

    /**
     * 上架
     *
     * @param skuId
     */
    void onSale(Long skuId);

    /**
     * 下架
     *
     * @param skuId
     */
    void offSale(Long skuId);

    /**
     * 热度排名 zset
     *
     * @param skuId
     */
    void incrHotScore(Long skuId);

    /**
     * 搜索
     *
     * @param searchParam
     * @return
     */
    SearchResponseVo searchProduct(SearchParam searchParam);
}
