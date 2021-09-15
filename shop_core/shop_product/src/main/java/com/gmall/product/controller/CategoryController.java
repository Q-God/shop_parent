package com.gmall.product.controller;

import com.alibaba.fastjson.JSONObject;
import com.gmall.entity.BaseCategory1;
import com.gmall.entity.BaseCategory2;
import com.gmall.entity.BaseCategory3;
import com.gmall.product.service.ManageService;
import com.gmall.util.common.result.RetVal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version v1.0
 * @ClassName CategoryController
 * @Description TODO
 * @Author Q
 */
@Api("分类API")
@RestController
@RequestMapping("/product")
public class CategoryController {

    @Autowired
    private ManageService manageService;

    @ApiOperation("查询一级分类")
    @GetMapping("/getCategory1")
    public RetVal getCategory1() {
        List<BaseCategory1> category1List = manageService.getCategory1();
        return RetVal.ok(category1List);

    }

    @ApiOperation("查询二级分类")
    @GetMapping("/getCategory2/{category1Id}")
    public RetVal getCategory2(@PathVariable Long category1Id) {
        List<BaseCategory2> category2List = manageService.getCategory2(category1Id);
        return RetVal.ok(category2List);
    }

    @ApiOperation("查询三级分类")
    @GetMapping("/getCategory3/{category2Id}")
    public RetVal getCategory3(@PathVariable Long category2Id) {
        List<BaseCategory3> category3List = manageService.getCategory3(category2Id);
        return RetVal.ok(category3List);
    }

    @ApiOperation("查询分类")
    @GetMapping("/getCategoryList")
    public List getCategoryList() {
        List<JSONObject> categoryList = manageService.getCategoryList();
        return categoryList;
    }
}
