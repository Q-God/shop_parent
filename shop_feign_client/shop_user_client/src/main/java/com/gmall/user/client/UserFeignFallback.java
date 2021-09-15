package com.gmall.user.client;

import com.gmall.entity.UserAddress;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version v1.0
 * @ClassName UserFallback
 * @Description TODO
 * @Author Q
 */
@Component
public class UserFeignFallback implements UserFeignClient {
    @Override
    public List<UserAddress> getAddressByUserId(String userId) {
        return null;
    }
}
