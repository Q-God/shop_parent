package com.gmall.product.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.entity.SkuInfo;
import com.gmall.product.service.ManageService;
import com.gmall.util.common.result.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version v1.0
 * @ClassName SkuController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/product")
public class SkuController {

    @Autowired
    private ManageService manageService;

    @PostMapping("/saveSkuInfo")
    public RetVal saveSkuInfo(@RequestBody SkuInfo skuInfo) {
        manageService.saveSkuInfo(skuInfo);
        return RetVal.ok();
    }

    @GetMapping("/querySkuInfoByPage/{currentPage}/{pageSize}")
    public RetVal getSkuInfoPage(@PathVariable Long currentPage,
                                 @PathVariable Long pageSize) {
        Page<SkuInfo> pageParam = new Page<>(currentPage, pageSize);
        Page<SkuInfo> skuInfoPage = manageService.getSkuInfoPage(pageParam);
        return RetVal.ok(skuInfoPage);
    }


    @GetMapping("/onSale/{skuId}")
    public RetVal onSale(@PathVariable Long skuId) {
        manageService.onSale(skuId);
        return RetVal.ok();
    }

    @GetMapping("offSale/{skuId}")
    public RetVal offSale(@PathVariable Long skuId) {
        manageService.offSale(skuId);
        return RetVal.ok();
    }
}
