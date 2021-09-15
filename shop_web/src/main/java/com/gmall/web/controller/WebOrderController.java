package com.gmall.web.controller;

import com.gmall.order.client.OrderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @version v1.0
 * @ClassName WebOrderController
 * @Description TODO
 * @Author Q
 */
@Controller
public class WebOrderController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @GetMapping("/confirm.html")
    public String confirm(Model model) {
        Map<String, Object> confirm = orderFeignClient.confirm();
        model.addAllAttributes(confirm);
        return "order/confirm";
    }
}
