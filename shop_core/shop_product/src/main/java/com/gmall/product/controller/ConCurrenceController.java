package com.gmall.product.controller;

import com.gmall.product.service.BaseBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version v1.0
 * @ClassName ConCurrenceController
 * @Description 分布式锁
 * @Author Q
 */
@RestController
public class ConCurrenceController {

    @Autowired
    private BaseBrandService baseBrandService;


}
