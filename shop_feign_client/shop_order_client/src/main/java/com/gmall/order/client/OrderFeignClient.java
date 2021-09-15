package com.gmall.order.client;

import com.gmall.entity.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(value = "shop-order", fallback = OrderFeignFallBack.class)
public interface OrderFeignClient {
    //1.订单确认信息提供数据接口
    @GetMapping("/order/confirm")
    Map<String, Object> confirm();

    //2.根据订单id查询订单信息
    @GetMapping("/order/getOrderInfo/{orderId}")
    OrderInfo getOrderInfo(@PathVariable Long orderId);

}
