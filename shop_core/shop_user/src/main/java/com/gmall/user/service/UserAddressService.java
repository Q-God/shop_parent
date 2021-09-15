package com.gmall.user.service;

import com.gmall.entity.UserAddress;

import java.util.List;

public interface UserAddressService {

    /**
     * 查询用户收货地址列表
     *
     * @param userId
     * @return
     */
    List<UserAddress> getUserAddressList(String userId);
}
