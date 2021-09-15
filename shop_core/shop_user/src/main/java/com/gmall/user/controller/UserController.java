package com.gmall.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.gmall.entity.UserInfo;
import com.gmall.user.service.UserInfoService;
import com.gmall.util.common.result.RetVal;
import com.gmall.util.common.util.IpUtil;
import com.gmall.utils.core.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version v1.0
 * @ClassName UserController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public RetVal login(@RequestBody UserInfo userInfo, HttpServletRequest request) {
        UserInfo dbUser = userInfoService.selectUserFromDB(userInfo);
        if (!ObjectUtils.isEmpty(dbUser)) {
            String token = UUID.randomUUID().toString();
            HashMap<String, Object> retValMap = new HashMap<>();
            retValMap.put("token", token);
            retValMap.put("nickName", dbUser.getNickName());

            JSONObject loginInfo = new JSONObject();
            loginInfo.put("userId", dbUser.getId());
            loginInfo.put("loginIP", IpUtil.getIpAddress(request));
            String userKey = RedisConst.USER_LOGIN_KEY_PREFIX + token;
            redisTemplate.opsForValue().set(userKey, loginInfo.toJSONString(), RedisConst.USERKEY_TIMEOUT, TimeUnit.SECONDS);
            return RetVal.ok(retValMap);
        } else {
            return RetVal.fail().message("登陸失敗");
        }
    }

    @GetMapping("/logout")
    public RetVal logout(@RequestHeader("token") String token) {
        String userKey = RedisConst.USER_LOGIN_KEY_PREFIX + token;
        redisTemplate.delete(userKey);
        return RetVal.ok();
    }


}
