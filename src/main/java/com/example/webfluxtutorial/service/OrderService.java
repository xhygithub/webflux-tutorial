package com.example.webfluxtutorial.service;

import com.example.webfluxtutorial.client.OrderClient;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderClient orderClient;

    public Mono<List<ServiceRecord>> getAllOrders() {
        return orderClient.getAllOrders().switchIfEmpty(Mono.just(Collections.emptyList()));
    }
}
