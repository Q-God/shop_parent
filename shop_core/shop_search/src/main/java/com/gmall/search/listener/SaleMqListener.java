package com.gmall.search.listener;

import com.gmall.search.service.SearchService;
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
 * @ClassName SaleMqListener
 * @Description TODO
 * @Author Q
 */
@Component
public class SaleMqListener {

    @Autowired
    private SearchService searchService;

    //订阅上架消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.ON_SALE_QUEUE, durable = "false"),
            exchange = @Exchange(value = MqConst.ON_OFF_SALE_EXCHANGE, durable = "false"),
            key = {MqConst.ON_SALE_ROUTING_KEY}
    ))
    public void onSale(Long skuId, Message message, Channel channel) throws IOException {
        if (!ObjectUtils.isEmpty(skuId)) {
            searchService.onSale(skuId);
            /**
             * 手动签收一把
             * deliveryTag签收哪个消息
             * multiple 是否应答多个消息 true应答签收多个消息，false只签收当前消息
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    //订阅下1架消息
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.OFF_SALE_QUEUE, durable = "false"),
            exchange = @Exchange(value = MqConst.ON_OFF_SALE_EXCHANGE, durable = "false"),
            key = {MqConst.OFF_SALE_ROUTING_KEY}
    ))
    public void offSale(Long skuId, Message message, Channel channel) throws IOException {
        searchService.offSale(skuId);
        /**
         * 手动签收一把
         * deliveryTag签收哪个消息
         * multiple 是否应答多个消息 true应答签收多个消息，false只签收当前消息
         */
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
