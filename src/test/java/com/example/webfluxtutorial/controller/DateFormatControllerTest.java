package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.WebfluxTestBase;
import com.example.webfluxtutorial.controller.dto.Order;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Optional;

class DateFormatControllerTest extends WebfluxTestBase {
    @MockBean
    OrderService orderService;

    @Test
    void should_return_wanted_date_json_when_call_api() {
        ServiceRecord serviceRecord = ServiceRecord.builder()
                .orderNumber("ooo")
                .lastChangeDate(new Date(1527767665L * 1000)).build();

        Mockito.when(orderService.getAllOrders())
                .thenReturn(Mono.just(List.of(serviceRecord)));
        testClient.get().uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/date/dateFormat")
                                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("[0].lastChangeDate").isEqualTo("2018-05-31T19:54:25.000Z");
    }

    @Test
    void should_return_right_json_when_call_api() {
        Mockito.when(orderService.getOptionalOrder())
                .thenReturn(Mono.just(Optional.of(Order.builder().orderNumber("oo").build())));

        testClient.get().uri(
                        uriBuilder ->
                                uriBuilder
                                        .path("/date/optional")
                                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.*").isEqualTo("oo");
    }
}