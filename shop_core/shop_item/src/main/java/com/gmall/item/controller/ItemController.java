package com.gmall.item.controller;

import com.gmall.item.service.ItemService;
import com.gmall.util.common.result.RetVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @version v1.0
 * @ClassName ItemController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("{skuId}")
    public RetVal getItem(@PathVariable Long skuId) {
        Map<String, Object> result = itemService.getBySkuId(skuId);
        return RetVal.ok(result);
    }


}
