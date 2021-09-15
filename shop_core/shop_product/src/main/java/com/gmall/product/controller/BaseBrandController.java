package com.gmall.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gmall.entity.BaseBrand;
import com.gmall.product.service.BaseBrandService;
import com.gmall.util.common.result.RetVal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version v1.0
 * @ClassName BaseBrandController
 * @Description TODO
 * @Author Q
 */
@Api(tags = "商标品牌Api")
@RestController
@RequestMapping("/product/brand")
public class BaseBrandController {

    @Autowired
    private BaseBrandService baseBrandService;

    @ApiOperation(value = "分页列表")
    @GetMapping("/queryBrandByPage/{currentPage}/{pageSize}")
    public RetVal index(@PathVariable Long currentPage,
                        @PathVariable Long pageSize) {
        Page<BaseBrand> pageParam = new Page<>(currentPage, pageSize);
        IPage<BaseBrand> baseBrandPage = baseBrandService.getBaseBrandPage(pageParam);
        return RetVal.ok(baseBrandPage);
    }

    @ApiOperation(value = "获取BaseBrand")
    @GetMapping("/{brandId}")
    public RetVal get(@PathVariable String brandId) {
        BaseBrand baseBrand = baseBrandService.getById(brandId);
        return RetVal.ok(baseBrand);
    }

    @ApiOperation(value = "新增BaseBrand")
    @PostMapping
    public RetVal saveBaseBrand(@RequestBody BaseBrand baseBrand) {
        baseBrandService.save(baseBrand);
        return RetVal.ok();
    }

    @ApiOperation(value = "修改BaseBrand")
    @PutMapping
    public RetVal updateBrand(@RequestBody BaseBrand baseBrand) {
        baseBrandService.updateById(baseBrand);
        return RetVal.ok();
    }


    @ApiOperation(value = "删除BaseBrand")
    @DeleteMapping("/{brandId}")
    public RetVal delete(@PathVariable Long brandId) {
        baseBrandService.removeById(brandId);
        return RetVal.ok();
    }

    @ApiOperation("查询全部品牌")
    @GetMapping("/getAllBrand")
    public RetVal<List<BaseBrand>> getAllBrand() {
        List<BaseBrand> baseBrandList = baseBrandService.list(null);
        return RetVal.ok(baseBrandList);
    }
}
