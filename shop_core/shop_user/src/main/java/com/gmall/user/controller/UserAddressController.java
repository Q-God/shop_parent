package com.gmall.user.controller;

import com.gmall.entity.UserAddress;
import com.gmall.user.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version v1.0
 * @ClassName UserAddressController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/user")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    /**
     * 根据用户id查询用户收货地址
     *
     * @param userId 用户Id
     * @return 用户收货地址列表
     */
    @GetMapping("getAddressByUserId/{userId}")
    public List<UserAddress> getAddressByUserId(@PathVariable String userId) {
        return userAddressService.getUserAddressList(userId);
    }

}
