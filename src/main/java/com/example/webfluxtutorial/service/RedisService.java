package com.example.webfluxtutorial.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Slf4j
@Service
@AllArgsConstructor
public class RedisService {

    private final ReactiveRedisTemplate<String, Object> reactiveRedisTemplate;

    public Mono<Object> getValueByKey(String key) {
        return reactiveRedisTemplate.opsForValue().get(key);
    }

    public Mono<Object> getAndSetNewValue(String key, HashMap<String, String> newValue) {
        return reactiveRedisTemplate.opsForValue().getAndSet(key, newValue)
                .doOnNext(oldValue -> {
                    log.info("the old value of key is: {}", oldValue);
                })
                .then(reactiveRedisTemplate.opsForValue().get(key))
                .doOnNext(newSettingValue -> {
                    log.info("the new value of key is: {}", newSettingValue);
                });
    }

    public Mono<Boolean> hasKey(String key) {
        return reactiveRedisTemplate.hasKey(key)
                .doOnNext(hasValue -> {
                    if (hasValue) {
                        log.info("the key: {} exists in Redis", key);
                    } else {
                        log.info("the key: {} does not exist in Redis", key);
                    }
                });
    }

    public Mono<Boolean> setKeyValue(String key, String value) {
        return reactiveRedisTemplate.opsForValue().set(key, value);
    }
}
