package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.service.RedisService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@RestController
@RequestMapping(value = "redis-operation")
@AllArgsConstructor
public class RedisController {

  private final RedisService redisService;

  @GetMapping(value = "/setKey")
  public Mono<Boolean> setKeyValue() {
    return redisService.setKeyValue("key", "value");
  }

  @GetMapping(value = "/getValueByKey")
  public Mono<Object> getValueByKey() {
    return redisService.getValueByKey("key");
  }

  @GetMapping(value = "/hasKey")
  public Mono<Boolean> hasKey() {
    return redisService.hasKey("key");
  }

  @GetMapping(value = "/setAndGet")
  public Mono<Object> setAndGet() {
    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("1", "hello");
    hashMap.put("2", "world");
    return redisService.getAndSetNewValue("key", hashMap);
  }
}
