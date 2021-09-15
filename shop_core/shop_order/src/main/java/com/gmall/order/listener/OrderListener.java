package com.gmall.order.listener;

import com.alibaba.fastjson.JSON;
import com.gmall.entity.OrderInfo;
import com.gmall.entity.PaymentInfo;
import com.gmall.enums.OrderStatus;
import com.gmall.enums.PaymentStatus;
import com.gmall.enums.ProcessStatus;
import com.gmall.order.service.OrderInfoService;
import com.gmall.payment.client.PaymentFeignClient;
import com.gmall.utils.core.constant.MqConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName OrderListener
 * @Description TODO
 * @Author Q
 */
@Component
public class OrderListener {

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private PaymentFeignClient paymentFeignClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = MqConst.CANCEL_ORDER_QUEUE)
    public void cancel(Long orderId, Message message, Channel channel) throws IOException {
        if (!ObjectUtils.isEmpty(orderId)) {
            //根据订单id查询订单信息
            OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
            if (!ObjectUtils.isEmpty(orderId) && orderInfo.getOrderStatus().equals(OrderStatus.UNPAID.name())) {
                //修改订单状态为已关闭
                orderInfoService.updateOrderStatus(orderInfo, ProcessStatus.CLOSED);
                //关闭支付订单信息
                PaymentInfo paymentInfo = paymentFeignClient.getPaymentInfo(orderInfo.getOutTradeNo());
                if (!ObjectUtils.isEmpty(paymentInfo) && paymentInfo.getPaymentStatus().equals(PaymentStatus.UNPAID.name())) {
                    //发送一条消息给支付系统
                    rabbitTemplate.convertAndSend(MqConst.CLOSE_PAYMENT_EXCHANGE, MqConst.CLOSE_PAYMENT_ROUTE_KEY, orderId);
                    boolean flag = paymentFeignClient.selectAlipayTrade(orderId);
                    if (flag) {
                        //关闭支付宝交易记录
                        paymentFeignClient.closeAlipayTrade(orderId);
                    }
                }
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.PAY_ORDER_QUEUE, durable = "false"),
            exchange = @Exchange(value = MqConst.PAY_ORDER_EXCHANGE, durable = "false"),
            key = {MqConst.PAY_ORDER_ROUTE_KEY}
    ))
    public void updateOrderAfterPaySuccess(Long orderId, Message message, Channel channel) {
        if (!ObjectUtils.isEmpty(orderId)) {
            OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
            if (!ObjectUtils.isEmpty(orderInfo) && orderInfo.getOrderStatus().equals(PaymentStatus.UNPAID.name())) {
                //修改订单状态为已关闭
                orderInfoService.updateOrderStatus(orderInfo, ProcessStatus.CLOSED);
                //通知仓库系统去减库存
                orderInfoService.sendMagToWareHouse(orderInfo);
            }
        }
        //根据订单id查询订单信息
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);

    }

    //3.仓库系统减库存成功之后的消费代码
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(MqConst.SUCCESS_DECREASE_STOCK_QUEUE),
            exchange = @Exchange(MqConst.SUCCESS_DECREASE_STOCK_EXCHANGE),
            key = MqConst.SUCCESS_DECREASE_STOCK_ROUTE_KEY
    ))
    public void updateOrderStatus(String msgJson, Message message, Channel channel) throws IOException {
        if (!StringUtils.isEmpty(msgJson)) {
            //获取从仓库那边传递过来的信息
            Map<String, String> map = JSON.parseObject(msgJson, Map.class);
            String orderId = map.get("orderId");
            String status = map.get("status");
            OrderInfo orderInfo = orderInfoService.getOrderInfo(Long.parseLong(orderId));
            //如果仓库减库存成功 这边就应该把状态改为已发货
            if ("DEDUCTED".equals(status)) {
                orderInfoService.updateOrderStatus(orderInfo, ProcessStatus.WAITING_DELEVER);
            } else {
                //减库存失败 超卖
                orderInfoService.updateOrderStatus(orderInfo, ProcessStatus.STOCK_EXCEPTION);
            }
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
