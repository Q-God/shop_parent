package com.gmall.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * @version v1.0
 * @ClassName CorsConfig
 * @Description CorsFilter
 * @Author Q
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        //cors跨域配置对象
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //设置允许访问的网络 * 表示任意
        corsConfiguration.addAllowedOrigin("*");
        //设置是否从服务器获取cookie
        corsConfiguration.setAllowCredentials(true);
        //允许请求头信息 * 表示任意
        corsConfiguration.addAllowedHeader("*");
        //允许请求方法 * 表示任意
        corsConfiguration.addAllowedMethod("*");

        //配置资源对象
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        //注册cors过滤器对象
        return new CorsWebFilter(corsConfigurationSource);
    }
}
