package com.gmall.payment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @version v1.0
 * @ClassName PaymentApplication
 * @Description TODO
 * @Author Q
 */
@EnableConfigurationProperties
@SpringBootApplication
@EnableFeignClients("com.gmall")
@EnableDiscoveryClient
@MapperScan("com.gmall.payment.mapper")
@ComponentScan("com.gmall")
public class PaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentApplication.class, args);
    }
}
