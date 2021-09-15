package com.gmall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @version v1.0
 * @ClassName ProductAPPLICATION
 * @Description TODO
 * @Author Q
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.gmall")
@MapperScan("com.gmall.product.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients("com.gmall")
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
