package com.gmall.compleable;

import com.google.common.collect.Lists;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

/**
 * @version v1.0
 * @ClassName Future
 * @Description TODO
 * @Author Q
 */
public class Future {
    public static void main(String[] args) {
        RedisTemplate redisTemplate = new RedisTemplate<String, Object>();
        System.out.println(redisTemplate.opsForValue().get("cache:37"));
        ArrayList<String> characterList = Lists.newArrayList("a", "b", "c", "d");
        characterList.stream().map(String::toUpperCase).forEach(System.out::print);

        serial();
    }

    private static void serial() {
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("我是串行化");
            return 1024;
        }).thenApply((applyValue) -> {
            System.out.println(applyValue);
            return applyValue;
        });
    }
}
