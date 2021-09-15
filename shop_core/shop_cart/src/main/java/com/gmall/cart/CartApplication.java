package com.gmall.cart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @version v1.0
 * @ClassName CartApplication
 * @Description TODO
 * @Author Q
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
@EnableFeignClients("com.gmall")
@MapperScan("com.gmall.cart.mapper")
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}
