package com.example.webfluxtutorial.service;

import com.example.webfluxtutorial.client.OrderClient;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Mock
    OrderClient orderClient;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_return_orders() {
        when(orderClient.getAllOrders()).thenReturn(Mono.just(
                Arrays.asList(ServiceRecord.builder().serviceOrderId("serviceOrderId").build())
        ));
        Mono<List<ServiceRecord>> allOrders = orderService.getAllOrders();
        StepVerifier.create(allOrders).consumeNextWith(serviceRecords -> {
            assertThat(serviceRecords.size()).isEqualTo(1);
            assertThat(serviceRecords.get(0).getServiceOrderId()).isEqualTo("serviceOrderId");
        }).verifyComplete();
    }

    @Test
    void should_return_empty_orders() {
        when(orderClient.getAllOrders()).thenReturn(Mono.empty());
        Mono<List<ServiceRecord>> allOrders = orderService.getAllOrders();
        StepVerifier.create(allOrders).consumeNextWith(serviceRecords -> {
            assertThat(serviceRecords.size()).isEqualTo(0);
        }).verifyComplete();
    }
}