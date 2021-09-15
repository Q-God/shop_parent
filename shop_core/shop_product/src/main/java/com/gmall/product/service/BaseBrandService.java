package com.gmall.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gmall.entity.BaseBrand;

public interface BaseBrandService extends IService<BaseBrand> {

    /**
     * 商标品牌分页
     *
     * @param pageParam
     * @return
     */
    IPage<BaseBrand> getBaseBrandPage(Page<BaseBrand> pageParam);

    /**
     * 通过brandId获取品牌信息
     * @param brandId
     * @return
     */
    BaseBrand getBaseBrandById(Long brandId);


}
