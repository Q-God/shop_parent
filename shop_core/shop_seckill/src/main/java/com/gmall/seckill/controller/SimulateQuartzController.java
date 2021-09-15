package com.gmall.seckill.controller;

import com.gmall.util.common.result.RetVal;
import com.gmall.utils.core.constant.MqConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version v1.0
 * @ClassName SimulateQuartzController
 * @Description TODO
 * @Author Q
 */
@RestController
public class SimulateQuartzController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendMsgToScanSeckill")
    public RetVal sendMsgToScanSeckill() {
        //只起到一个通知该上架了 所以这里消息随便发什么消息
        rabbitTemplate.convertAndSend(MqConst.SCAN_SECKILL_EXCHANGE, MqConst.SCAN_SECKILL_ROUTE_KEY, "zz");
        return RetVal.ok().message("success");
    }
}
