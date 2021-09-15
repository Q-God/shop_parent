package com.gmall.seckill.util;

import com.gmall.utils.core.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @version v1.0
 * @ClassName RedisMessageReceiver
 * @Description TODO
 * @Author Q
 */
@Component
public class RedisMessageReceiver {

    @Autowired
    private RedisTemplate redisTemplate;

    public void receiveChannelMessage(String message) {
        if (!StringUtils.isEmpty(message)) {
            message = message.replaceAll("\"", "");
            String[] splitMessage = message.split(":");
            if (splitMessage.length == 2) {
                redisTemplate.opsForValue().set(RedisConst.SECKILL_STATE_PREFIX + splitMessage[0], splitMessage[1]);
            }
        }
    }
}

