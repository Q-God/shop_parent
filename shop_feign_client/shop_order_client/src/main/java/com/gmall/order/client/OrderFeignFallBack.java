package com.gmall.order.client;

import com.gmall.entity.OrderInfo;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @version v1.0
 * @ClassName CartFeignFallBack
 * @Description TODO
 * @Author Q
 */
@Component
public class OrderFeignFallBack implements OrderFeignClient {

    @Override
    public Map<String, Object> confirm() {
        return null;
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return null;
    }
}
