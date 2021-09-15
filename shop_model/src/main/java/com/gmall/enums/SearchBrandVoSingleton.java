package com.gmall.enums;

import com.gmall.search.SearchBrandVo;

/**
 * @version v1.0
 * @ClassName SearchBrandVoSingleton
 * @Description TODO
 * @Author Q
 */
public enum SearchBrandVoSingleton {
    INSTANCE;

    private final SearchBrandVo searchBrandVo;

    //私有化枚举的构造函数
    SearchBrandVoSingleton() {
        searchBrandVo = new SearchBrandVo();
    }

    //暴露共有方法给MyExecutor
    public SearchBrandVo getInstance() {
        return searchBrandVo;
    }
}
