package com.gmall.seckill.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("shop-seckill")
public interface SeckillFeignClient {
}
