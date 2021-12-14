package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.WebfluxTestBase;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class OrderControllerTest extends WebfluxTestBase {
    @MockBean
    OrderService orderService;

    @Test
    void should_return_empty_service_record() {
        when(orderService.getAllOrders()).thenReturn(Mono.error(new Exception("error happens")));

        Flux<List> orders = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(List.class)
                .getResponseBody();
        StepVerifier.create(orders).expectNextCount(0).expectComplete();
    }

    @Test
    void should_return_service_records() {
        when(orderService.getAllOrders()).thenReturn(Mono.just(
                Arrays.asList(
                        ServiceRecord.builder().serviceOrderId("1").build(),
                        ServiceRecord.builder().serviceOrderId("2").build()
                )
        ));

        testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders").build())
                .exchange()
                .expectStatus()
                .isOk();
    }
}