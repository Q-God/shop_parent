package com.gmall.order.config;

import com.gmall.utils.core.constant.MqConst;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName RabbitMqConfig
 * @Description TODO
 * @Author Q
 */
@Configuration
public class DealyMqConfig {

    @Bean
    public Queue cancelOrderQueue() {
        return new Queue(MqConst.CANCEL_ORDER_QUEUE);
    }

    @Bean
    public CustomExchange cancelOrderExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(MqConst.CANCEL_ORDER_EXCHANGE, "x-delayed-message", false, true, args);
    }

    @Bean
    public Binding bindingDealyedQueue(@Qualifier("cancelOrderQueue") Queue cancelOrderQueue, @Qualifier("cancelOrderExchange") CustomExchange cancelOrderExchange) {
        return BindingBuilder.bind(cancelOrderQueue).to(cancelOrderExchange).with(MqConst.CANCEL_ORDER_ROUTE_KEY).noargs();
    }
}
