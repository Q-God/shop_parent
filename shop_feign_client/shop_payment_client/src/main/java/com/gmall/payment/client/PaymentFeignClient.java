package com.gmall.payment.client;

import com.gmall.entity.PaymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("shop-payment")
public interface PaymentFeignClient {

    //发起退款接口
    @RequestMapping("/payment/refund/{orderId}")
    boolean refund(@PathVariable Long orderId);

    //查询支付宝中是否有交易记录
    @RequestMapping("/payment/selectAlipayTrade/{orderId}")
    boolean selectAlipayTrade(@PathVariable Long orderId);


    //关闭交易
    @GetMapping("/payment/closeAlipayTrade/{orderId}")
    boolean closeAlipayTrade(@PathVariable Long orderId);

    //查询支付订单信息
    @GetMapping("/payment/getPaymentInfo/{outTradeNo}")
    PaymentInfo getPaymentInfo(@PathVariable String outTradeNo);

}
