package com.gmall.user.client;

import com.gmall.entity.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "shop-user", fallback = UserFeignFallback.class)
public interface UserFeignClient {

    @GetMapping("/user/getAddressByUserId/{userId}")
    List<UserAddress> getAddressByUserId(@PathVariable String userId);
}
