package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<ServiceRecord>> getAllOrders() {
        return orderService.getAllOrders()
                .onErrorResume(error -> {
                    log.error("no orders find");
                    return Mono.just(Collections.emptyList());
                });
    }
}
