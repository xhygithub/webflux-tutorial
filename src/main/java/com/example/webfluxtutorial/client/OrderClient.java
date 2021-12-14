package com.example.webfluxtutorial.client;

import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import org.springframework.web.bind.annotation.GetMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;

@ReactiveFeignClient(name = "order-management")
public interface OrderClient {
    @GetMapping(value = "/orders")
    Mono<List<ServiceRecord>> getAllOrders();
}
