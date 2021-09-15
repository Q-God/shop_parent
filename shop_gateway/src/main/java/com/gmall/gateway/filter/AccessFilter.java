package com.gmall.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.gmall.util.common.result.RetVal;
import com.gmall.util.common.result.RetValCodeEnum;
import com.gmall.util.common.util.IpUtil;
import com.gmall.utils.core.constant.RedisConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @version v1.0
 * @ClassName AccessFilter
 * @Description TODO
 * @Author Q
 */
@Component
public class AccessFilter implements GlobalFilter {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("#{'${filter.whitelist}'.split(',')}")
    private List<String> whiteList;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //1
        if (antPathMatcher.match("/sku/**", path)) {
            return responseToBrowser(exchange, RetValCodeEnum.NO_PERMISSION);
        }
        //2.如果用户登录了拿到用户id判断该用户是否为同一IP
        String userId = getUserId(request);
        String userTempId = getUserTempId(request);
        if ("-1".equals(userId)) {
            return responseToBrowser(exchange, RetValCodeEnum.NO_PERMISSION);
        }
        //3.对于指定的某些(我的订单/我的购物车/我xxx)资源,必须登录
        if (antPathMatcher.match("/order/**", path)) {
            if (StringUtils.isEmpty(userId)) {
                return responseToBrowser(exchange, RetValCodeEnum.NO_LOGIN);
            }
        }
        //4.用户请求白名单 如果请求是白名单 就直接跳转到登录页面
        for (String item : whiteList) {
            if (path.indexOf(item) != -1 && ObjectUtils.isEmpty(userId)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.SEE_OTHER);
                response.getHeaders().set(HttpHeaders.LOCATION, "http://passport.gmall.com/login.html?originalUrl=" + request.getURI());
                return response.setComplete();
            }
        }

        //5.把用户id需要保存到header中 传给shop-web那边的request
        if (!StringUtils.isEmpty(userId) || !StringUtils.isEmpty(userTempId)) {
            if (!StringUtils.isEmpty(userId)) {
                request.mutate().header("userId", userId).build();
            }
            if (!StringUtils.isEmpty(userTempId)) {
                request.mutate().header("userTempId", userTempId).build();
            }
            return chain.filter(exchange.mutate().request(request).build());
        }
        return chain.filter(exchange);
    }


    /**
     * 获取用户UserId
     *
     * @param request
     * @return
     */
    private String getUserId(ServerHttpRequest request) {
        List<String> headerValueList = request.getHeaders().get("token");
        String token = null;
        if (!CollectionUtils.isEmpty(headerValueList)) {
            token = headerValueList.get(0);
        } else {
            HttpCookie cookie = request.getCookies().getFirst("token");
            if (!ObjectUtils.isEmpty(cookie)) {
                token = cookie.getValue();
            }
        }

        if (!StringUtils.isEmpty(token)) {
            String userKey = RedisConst.USER_LOGIN_KEY_PREFIX + token;
            Boolean aBoolean = redisTemplate.hasKey(userKey);
            System.out.println("aBoolean = " + aBoolean);
            String loginInfoJson = redisTemplate.opsForValue().get(userKey);
            if (!StringUtils.isEmpty(loginInfoJson)) {
                JSONObject loginJsonObject = JSONObject.parseObject(loginInfoJson);
                String loginIp = loginJsonObject.getString("loginIP");
                //拿到当前请求的ip地址
                String requestIpAddress = IpUtil.getGatwayIpAddress(request);
                if (loginIp.equals(requestIpAddress)) {
                    return loginJsonObject.getString("userId");
                } else {
                    return "-1";
                }
            }

        }
        return null;
    }

    /**
     * 可读性低
     *
     * @param request
     * @return
     */
    private String getUserTempId(ServerHttpRequest request) {
        return !ObjectUtils.isEmpty(request.getHeaders().get("userTempId")) ?
                request.getHeaders().get("userTempId").get(0) :
                !ObjectUtils.isEmpty(request.getCookies().getFirst("userTempId")) ?
                        request.getCookies().getFirst("userTempId").getValue() : null;
    }

    /**
     * 响应给前端
     *
     * @param exchange
     * @param retValCodeEnum
     * @return
     */
    public Mono<Void> responseToBrowser(ServerWebExchange exchange, RetValCodeEnum retValCodeEnum) {
        ServerHttpResponse response = exchange.getResponse();
        RetVal<Object> retVal = RetVal.build(null, retValCodeEnum);
        //把该retVal转换为json字符串
        byte[] body = JSONObject.toJSONString(retVal).getBytes(StandardCharsets.UTF_8);
        //将字节转换为一个数据buffer 效率高
        DataBuffer dataBuffer = response.bufferFactory().wrap(body);
        //设置返回给浏览器的请求头 数据类型是json字符串
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        //把数据发射给浏览器
        return response.writeWith(Mono.just(dataBuffer));
    }
}
