package com.gmall.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gmall.entity.UserInfo;
import com.gmall.user.mapper.UserInfoMapper;
import com.gmall.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @version v1.0
 * @ClassName UserInfoServiceImpl
 * @Description TODO
 * @Author Q
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectUserFromDB(UserInfo DBUserInfo) {
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getLoginName, DBUserInfo.getLoginName());
        String password = DBUserInfo.getPasswd();
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        wrapper.eq(UserInfo::getPasswd, md5Password);
        return userInfoMapper.selectOne(wrapper);
    }
}
