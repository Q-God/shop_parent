package com.gmall.product.controller;


import com.gmall.entity.PlatformPropertyKey;
import com.gmall.entity.PlatformPropertyValue;
import com.gmall.product.service.ManageService;
import com.gmall.util.common.result.RetVal;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 一级分类表 前端控制器
 * </p>
 *
 * @author Q
 * @since 2021-08-21
 */
@ApiOperation("")
@RestController
@RequestMapping("/product")
public class PlatFormPropertyController {

    @Autowired
    private ManageService manageService;

    @GetMapping("/getPlatformPropertyByCategoryId/{category1Id}/{category2Id}/{category3Id}")
    public RetVal getPlatformPropertyByCategoryId(
            @PathVariable Long category1Id,
            @PathVariable Long category2Id,
            @PathVariable Long category3Id) {
        List<PlatformPropertyKey> platformPropertyValueList = manageService.getPlatformPropertyValueList(category1Id, category2Id, category3Id);
        return RetVal.ok(platformPropertyValueList);
    }

    @PostMapping("/savePlatformProperty")
    public RetVal savePlatformProperty(@RequestBody PlatformPropertyKey platformPropertyKey) {
        manageService.savePlatformPropertyKey(platformPropertyKey);
        return RetVal.ok();
    }

    @GetMapping("/getPropertyValueByPropertyKeyId/{platformPropertyKeyId}")
    public RetVal<List<PlatformPropertyValue>> getPropertyValueByPropertyKeyId(@PathVariable Long platformPropertyKeyId) {
        PlatformPropertyKey platformPropertyKey = manageService.getPlatformPropertyKey(platformPropertyKeyId);
        List<PlatformPropertyValue> propertyValueList = platformPropertyKey.getPropertyValueList();
        return RetVal.ok(propertyValueList);
    }

    @GetMapping("getPlatformPropertyBySkuId/{skuId}")
    public List<PlatformPropertyKey> getPlatformPropertyBySkuId(@PathVariable Long skuId) {
        return manageService.getPlatformPropertyBySkuId(skuId);
    }

}

