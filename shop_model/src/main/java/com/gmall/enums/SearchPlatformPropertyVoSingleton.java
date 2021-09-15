package com.gmall.enums;

import com.gmall.search.SearchPlatformPropertyVo;

/**
 * @version v1.0
 * @ClassName SearchPlatformPropertyVoSingleton
 * @Description TODO
 * @Author Q
 */
public enum SearchPlatformPropertyVoSingleton {
    INSTANCE;
    private final SearchPlatformPropertyVo searchPlatformPropertyVo;

    SearchPlatformPropertyVoSingleton() {
        searchPlatformPropertyVo = new SearchPlatformPropertyVo();
    }

    public SearchPlatformPropertyVo getInstance() {
        return this.searchPlatformPropertyVo;
    }

}
