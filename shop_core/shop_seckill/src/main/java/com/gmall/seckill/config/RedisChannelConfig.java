package com.gmall.seckill.config;

import com.gmall.seckill.util.RedisMessageReceiver;
import com.gmall.utils.core.constant.RedisConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @version v1.0
 * @ClassName RedisChannelConfig
 * @Description TODO
 * @Author Q
 */
@Configuration
public class RedisChannelConfig {

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageReceiver redisMessageReceiver) {
        return new MessageListenerAdapter(redisMessageReceiver);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        //订阅的主题
        container.addMessageListener(messageListenerAdapter, new PatternTopic(RedisConst.PREPARE_PUB_SUB_SECKILL));

        return container;
    }
}
