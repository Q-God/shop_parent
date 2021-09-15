package com.gmall.item.service.impl;

import com.gmall.item.service.ItemService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName ItemService
 * @Description TODO
 * @Author Q
 */
@Service
public class ItemServiceImpl implements ItemService {
    /**
     * 获取sku详情信息
     *
     * @param skuId
     * @return
     */
    @Override
    public Map<String, Object> getBySkuId(Long skuId) {
        Map<String, Object> result = new HashMap<>();


        return result;
    }
}
