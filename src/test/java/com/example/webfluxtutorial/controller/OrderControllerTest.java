package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.WebfluxTestBase;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class OrderControllerTest extends WebfluxTestBase {
    @MockBean
    OrderService orderService;

    @Test
    void should_return_emppty_service_record() {
        when(orderService.getAllOrders()).thenReturn(Mono.error(new Exception("error happens")));

        Flux<ServiceRecord> orders = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("orders").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(orders).consumeNextWith(records -> {
            assertThat(records.getDealerId()).isNull();
        }).verifyComplete();

    }
}