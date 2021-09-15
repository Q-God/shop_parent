package com.gmall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.entity.BaseSaleProperty;
import com.gmall.entity.ProductImage;
import com.gmall.entity.ProductSalePropertyKey;
import com.gmall.entity.ProductSpu;
import com.gmall.product.service.ManageService;
import com.gmall.util.common.result.RetVal;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version v1.0
 * @ClassName ProductSpuController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/product")
public class ProductSpuController {

    @Autowired
    private ManageService manageService;

    @GetMapping("/{page}/{size}")
    public RetVal getProductSpuPage(
            @PathVariable Long page,
            @PathVariable Long size,
            ProductSpu productSpu
    ) {
        //创建一个Page对象
        Page<ProductSpu> pageParam = new Page<>(page, size);
        IPage<ProductSpu> productSpuPage = manageService.getProductSpuPage(pageParam, productSpu);
        return RetVal.ok(productSpuPage);
    }

    @GetMapping("queryProductSpuByPage/{currentPage}/{pageSize}/{category3Id}")
    public RetVal queryProductSpuByPage(
            @PathVariable Long currentPage,
            @PathVariable Long pageSize,
            @PathVariable Long category3Id
    ) {
        //创建一个Page对象
        Page<ProductSpu> pageParam = new Page<>(currentPage, pageSize);
        IPage<ProductSpu> productSpuPage = manageService.getProductSpuByPage(pageParam, category3Id);
        return RetVal.ok(productSpuPage);
    }

    @GetMapping("/queryAllSaleProperty")
    public RetVal queryAllSaleProperty() {
        //查询所有的销售属性集合
        List<BaseSaleProperty> baseSalePropertyList = manageService.getBaseSalePropertyList();
        return RetVal.ok(baseSalePropertyList);
    }

    @ApiOperation("保存Spu")
    @PostMapping("/saveProductSpu")
    public RetVal saveProductSpu(@RequestBody ProductSpu productSpu) {
        //保存
        manageService.saveProductSpu(productSpu);
        return RetVal.ok();
    }

    @GetMapping("/queryProductImageByProductId/{productSpuId}")
    public RetVal<List<ProductImage>> queryProductImageByProductId(@PathVariable Long productSpuId) {
        List<ProductImage> productImage = manageService.getProductImage(productSpuId);
        return RetVal.ok(productImage);
    }

    @GetMapping("querySalePropertyByProductId/{productSpuId}")
    public RetVal<List<ProductSalePropertyKey>> querySalePropertyByProductId(@PathVariable Long productSpuId) {
        List<ProductSalePropertyKey> productSalePropertyKeyList = manageService.getProductSalePropertyKeyList(productSpuId);
        return RetVal.ok(productSalePropertyKeyList);
    }
}
