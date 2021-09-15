package com.gmall.web.controller;

import com.gmall.entity.OrderInfo;
import com.gmall.order.client.OrderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version v1.0
 * @ClassName WebPaymentController
 * @Description TODO
 * @Author Q
 */
@Controller
public class WebPaymentController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    //1.跳转到支付前置页面
    @GetMapping("pay.html")
    public String payment(@RequestParam Long orderId, Model model) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderId);
        model.addAttribute("orderInfo", orderInfo);
        return "payment/pay";
    }

    //2.支付成功之后实现页面跳转
    @GetMapping("alipay/success.html")
    public String success() {
        return "payment/success";
    }
}
