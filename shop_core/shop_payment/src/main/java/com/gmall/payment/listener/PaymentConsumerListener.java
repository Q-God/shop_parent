package com.gmall.payment.listener;

import com.gmall.entity.PaymentInfo;
import com.gmall.enums.PaymentStatus;
import com.gmall.payment.service.PaymentInfoService;
import com.gmall.utils.core.constant.MqConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

/**
 * @version v1.0
 * @ClassName PaymentConsumerListener
 * @Description TODO
 * @Author Q
 */
@Component
public class PaymentConsumerListener {

    @Autowired
    private PaymentInfoService paymentInfoService;

    //支付成功之后修改订单状态
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(MqConst.CLOSE_PAYMENT_QUEUE),
            exchange = @Exchange(MqConst.CLOSE_PAYMENT_EXCHANGE),
            key = MqConst.CLOSE_PAYMENT_ROUTE_KEY
    ))
    public void closePaymentInfo(Long orderId, Message message, Channel channel) throws IOException {
        if (!ObjectUtils.isEmpty(orderId)) {
            PaymentInfo paymentInfo = paymentInfoService.selectOne(orderId);
            if (!ObjectUtils.isEmpty(paymentInfo)) {
                //修改订单状态
                paymentInfo.setPaymentStatus(PaymentStatus.ClOSED.name());
                //保存
                paymentInfoService.update(paymentInfo);
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
