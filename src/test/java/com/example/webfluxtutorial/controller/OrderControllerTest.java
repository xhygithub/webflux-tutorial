package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.WebfluxTestBase;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.controller.dto.User;
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
    void should_return_empty_service_record_when_return_mono_error() {
        when(orderService.getAllOrders()).thenReturn(Mono.error(new Exception("error happens")));

        Flux<List> orders = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/useOnErrorResume").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(List.class)
                .getResponseBody();
        StepVerifier.create(orders).expectNextCount(0).expectComplete();
    }

    @Test
    void should_return_service_records_when_not_return_mono_error() {
        when(orderService.getAllOrders()).thenReturn(Mono.just(
                Arrays.asList(
                        ServiceRecord.builder().serviceOrderId("1").build(),
                        ServiceRecord.builder().serviceOrderId("2").build()
                )
        ));

        testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/useOnErrorResume").build())
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void should_return_service_record_when_call_zip_api(){
        when(orderService.useZip()).thenReturn(Mono.just(ServiceRecord.builder().id("id").build()));
        Flux<ServiceRecord> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/zip").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(records -> {
            assertThat(records.getId()).isEqualTo("id");
        }).verifyComplete();
    }

    @Test
    void should_return_service_record_when_call_zipAndMapIsNotPerformWhenGetMonoIsEmpty_api(){
        when(orderService.useZipAndMapIsNotPerformWhenGetMonoIsEmpty()).thenReturn(Mono.just(ServiceRecord.builder().id("id").build()));
        Flux<ServiceRecord> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/zipAndMapIsNotPerformWhenGetMonoIsEmpty").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(records -> {
            assertThat(records.getId()).isEqualTo("id");
        }).verifyComplete();
    }

    @Test
    void should_return_service_record_when_call_zipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault_api(){
        when(orderService.useZipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault()).thenReturn(Mono.just(ServiceRecord.builder().id("id").build()));
        Flux<ServiceRecord> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/zipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(records -> {
            assertThat(records.getId()).isEqualTo("id");
        }).verifyComplete();
    }

    @Test
    void should_return_service_record_when_call_zipWhenGetMonoIsOptional_api(){
        when(orderService.useZipWhenGetMonoIsOptional()).thenReturn(Mono.just(ServiceRecord.builder().id("id").build()));
        Flux<ServiceRecord> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/zipWhenGetMonoIsOptional").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(records -> {
            assertThat(records.getId()).isEqualTo("id");
        }).verifyComplete();
    }

    @Test
    void should_return_service_record_when_call_zipWith_api(){
        when(orderService.useZipWith()).thenReturn(Mono.just(ServiceRecord.builder().id("id").build()));
        Flux<ServiceRecord> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/zipWith").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(records -> {
            assertThat(records.getId()).isEqualTo("id");
        }).verifyComplete();
    }

    @Test
    void should_return_service_record_when_call_zipWhen_api(){
        when(orderService.useZipWhen()).thenReturn(Mono.just(ServiceRecord.builder().id("id").build()));
        Flux<ServiceRecord> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/zipWhen").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(ServiceRecord.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(records -> {
            assertThat(records.getId()).isEqualTo("id");
        }).verifyComplete();
    }

    @Test
    void should_return_user_when_call_flatMap_api(){
        when(orderService.useFlatMap()).thenReturn(Mono.just(User.builder().dealerId("dealerId").build()));
        Flux<User> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/flatMap").build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(User.class)
                .getResponseBody();
        StepVerifier.create(responseBody).consumeNextWith(user -> {
            assertThat(user.getDealerId()).isEqualTo("dealerId");
        }).verifyComplete();
    }
}