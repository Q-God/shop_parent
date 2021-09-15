package com.gmall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @version v1.0
 * @ClassName OrderApplication
 * @Description TODO
 * @Author Q
 */
@SpringBootApplication
@ComponentScan("com.gmall")
@MapperScan("com.gmall.order.mapper")
@EnableFeignClients("com.gmall")
@EnableDiscoveryClient
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);

    }
}
