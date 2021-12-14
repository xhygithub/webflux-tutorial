package com.example.webfluxtutorial.service;

import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderService {
    public Mono<List<ServiceRecord>> getAllOrders() {
        return Mono.just(Arrays.asList(
                ServiceRecord.builder().serviceOrderId("1").build(),
                ServiceRecord.builder().serviceOrderId("2").build()
        ));
    }
}
