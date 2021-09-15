package com.gmall.seckill.listener;

import com.gmall.entity.SeckillProduct;
import com.gmall.entity.UserSeckillSkuInfo;
import com.gmall.seckill.service.SeckillProductService;
import com.gmall.utils.core.constant.MqConst;
import com.gmall.utils.core.constant.RedisConst;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

/**
 * @version v1.0
 * @ClassName SecKillListener
 * @Description TODO
 * @Author Q
 */
@Component
public class SecKillListener {

    @Autowired
    private SeckillProductService seckillProductService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(MqConst.SCAN_SECKILL_EXCHANGE),
            value = @Queue(MqConst.SCAN_SECKILL_QUEUE),
            key = MqConst.SCAN_SECKILL_ROUTE_KEY
    ))
    public void scanSeckilProductToRedis(Message message, Channel channel) throws IOException {

        List<SeckillProduct> seckillProductList = seckillProductService.selectDayRecords();
        if (!CollectionUtils.isEmpty(seckillProductList)) {
            seckillProductList.stream().filter(seckillProduct -> !redisTemplate.boundHashOps(RedisConst.SECKILL_PRODUCT).hasKey(seckillProduct.getSkuId().toString())).forEach(seckillProduct -> {
                //判断该秒杀商品是否在redis中已经存在
                redisTemplate.boundHashOps(RedisConst.SECKILL_PRODUCT).put(seckillProduct.getSkuId().toString(), seckillProduct);
                for (int i = 0; i < seckillProduct.getNum(); i++) {
                    redisTemplate.boundListOps(RedisConst.SECKILL_STOCK_PREFIX + seckillProduct.getSkuId()).leftPush(seckillProduct.getSkuId());
                }
                redisTemplate.convertAndSend(RedisConst.PREPARE_PUB_SUB_SECKILL, RedisConst.CAN_SECKILL);

            });
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    //2.预下单的监听器
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MqConst.PREPARE_SECKILL_EXCHANGE, durable = "false"),
            value = @Queue(value = MqConst.PREPARE_SECKILL_QUEUE, durable = "false"),
            key = MqConst.PREPARE_SECKILL_ROUTE_KEY
    ))
    public void perpaseSeckill(UserSeckillSkuInfo userSeckillSkuInfo, Message message, Channel channel) throws IOException {
        if (ObjectUtils.isEmpty(userSeckillSkuInfo)) {
            seckillProductService.prepareSeckill(userSeckillSkuInfo);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MqConst.CLEAR_REDIS_EXCHANGE, durable = "false"),
            value = @Queue(value = MqConst.CLEAR_REDIS_QUEUE, durable = "false"),
            key = MqConst.CLEAR_REDIS_ROUTE_KEY
    ))
    public void clearSeckill(Message message, Channel channel) {

        List<SeckillProduct> seckillProductList = seckillProductService.selectAllExpired();
        seckillProductList.stream().forEach(seckillProduct -> {
            redisTemplate.delete(RedisConst.SECKILL_STOCK_PREFIX + seckillProduct.getSkuId());
            redisTemplate.delete(RedisConst.SECKILL_STATE_PREFIX + seckillProduct.getSkuId());
            seckillProduct.setStatus("2");
            seckillProductService.update(seckillProduct);
        });
        redisTemplate.delete(RedisConst.SECKILL_PRODUCT);

        redisTemplate.delete(RedisConst.PREPARE_SECKILL_USERID_ORDER);

        redisTemplate.delete(RedisConst.BOUGHT_SECKILL_USER_ORDER);
    }


}
