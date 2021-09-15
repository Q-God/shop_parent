package com.gmall.user.service;

import com.gmall.entity.UserInfo;

/**
 * @version v1.0
 * @ClassName UserInfoService
 * @Description TODO
 * @Author Q
 */
public interface UserInfoService {
    UserInfo selectUserFromDB(UserInfo DBUserInfo);
}
