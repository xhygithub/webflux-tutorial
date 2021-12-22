package com.example.webfluxtutorial.client;

import com.example.webfluxtutorial.config.HeaderInterceptorConfiguration;
import com.example.webfluxtutorial.controller.dto.Order;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@ReactiveFeignClient(name = "order-management", configuration = {HeaderInterceptorConfiguration.class})
public interface OrderClient {
    @GetMapping(value = "/orders")
    Mono<List<ServiceRecord>> getAllOrders();

    @GetMapping(value = "/order")
    Mono<Order> getOrder();

    @GetMapping(value = "/order/empty")
    Mono<Order> getEmptyOrder();

    @GetMapping(value = "/order/optional")
    Mono<Optional<Order>> getOptionalOrder();

    @DeleteMapping(value = "/order/{orderNumber}")
    Mono<Void> deleteOrderByOrderNumber(@PathVariable(name = "orderNumber") String orderNumber);

    @PatchMapping(value = "/order/{orderNumber}")
    Mono<Order> updateOrderByOrderNumber(@PathVariable(name = "orderNumber") String orderNumber);
}
