package com.example.webfluxtutorial.client;

import com.example.webfluxtutorial.controller.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@ReactiveFeignClient(name = "user-management")
public interface UserClient {
    @GetMapping(value = "/user")
    Mono<User> getUser();

    @GetMapping(value = "/user/empty")
    Mono<User> getEmptyUser();

    @GetMapping(value = "/user/optional")
    Mono<Optional<User>> getOptionalEmptyUser();
}
