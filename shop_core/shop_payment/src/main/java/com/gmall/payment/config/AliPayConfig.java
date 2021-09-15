package com.gmall.payment.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version v1.0
 * @ClassName AliPayConfig
 * @Description TODO
 * @Author Q
 */
@Configuration
public class AliPayConfig {

    public final static String format = "json";
    public final static String charset = "utf-8";
    public final static String sign_type = "RSA2";
    public static String return_payment_url;
    public static String notify_payment_url;
    public static String alipay_public_key;
    @Value("${alipay.alipay_url}")
    private String alipay_url;
    @Value("${alipay.app_id}")
    private String app_id;
    @Value("${alipay.app_private_key}")
    private String app_private_key;

    @Value("${alipay.alipay_public_key}")
    private void setAlipay_public_key(String alipay_public_key) {
        AliPayConfig.alipay_public_key = alipay_public_key;
    }

    @Value("${alipay.return_payment_url}")
    private void setReturn_payment_url(String return_payment_url) {
        AliPayConfig.return_payment_url = return_payment_url;
    }

    @Value("${alipay.notify_payment_url}")
    private void setNotify_payment_url(String notify_payment_url) {
        AliPayConfig.notify_payment_url = notify_payment_url;
    }

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(alipay_url, app_id, app_private_key, format, charset, alipay_public_key, sign_type);
    }

    @Bean
    public AlipayTradePagePayRequest alipayTradePagePayRequest() {
        return new AlipayTradePagePayRequest();
    }
}
