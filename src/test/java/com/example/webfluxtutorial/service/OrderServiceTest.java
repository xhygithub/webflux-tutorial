package com.example.webfluxtutorial.service;

import com.example.webfluxtutorial.client.OrderClient;
import com.example.webfluxtutorial.client.UserClient;
import com.example.webfluxtutorial.controller.dto.Order;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.controller.dto.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderClient orderClient;

    @Mock
    private UserClient userClient;

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

    @Test
    void should_return_right_service_record_when_use_zip(){
        when(orderClient.getOrder()).thenReturn(Mono.just(Order.builder().serviceOrderId("orderId").build()));
        when(userClient.getUser()).thenReturn(Mono.just(User.builder().dealerId("dealerId").build()));

        Mono<ServiceRecord> serviceRecordMono = orderService.useZip();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isEqualTo("orderId");
                    assertThat(serviceRecord.getDealerId()).isEqualTo("dealerId");
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zip_and_map_is_not_perform_and_get_mono_is_empty(){
        when(orderClient.getEmptyOrder()).thenReturn(Mono.empty());
        when(userClient.getEmptyUser()).thenReturn(Mono.empty());

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipAndMapIsNotPerformWhenGetMonoIsEmpty();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isNull();
                    assertThat(serviceRecord.getDealerId()).isNull();
                    assertThat(serviceRecord.getOrderNumber()).isEqualTo("mapIsNotPerform");
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zip_and_map_is_perform_and_get_mono_is_empty_but_has_default(){
        when(orderClient.getEmptyOrder()).thenReturn(Mono.empty());
        when(userClient.getEmptyUser()).thenReturn(Mono.empty());

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isEqualTo("tt");
                    assertThat(serviceRecord.getDealerId()).isNull();
                    assertThat(serviceRecord.getOrderNumber()).isNull();
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zip_and_map_is_perform_and_get_mono_is_optional(){
        when(orderClient.getOptionalOrder()).thenReturn(Mono.just(Optional.empty()));
        when(userClient.getOptionalEmptyUser()).thenReturn(Mono.just(Optional.of(User.builder().dealerId("dealerId").build())));

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipWhenGetMonoIsOptional();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isNull();
                    assertThat(serviceRecord.getDealerId()).isNull();
                    assertThat(serviceRecord.getOrderNumber()).isNull();
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zipWith(){
        when(orderClient.getOrder()).thenReturn(Mono.just(Order.builder().serviceOrderId("orderId").build()));
        when(userClient.getUser()).thenReturn(Mono.just(User.builder().dealerId("dealerId").build()));

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipWith();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isEqualTo("orderId");
                    assertThat(serviceRecord.getDealerId()).isEqualTo("dealerId");
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zipWith_empty_mono(){
        when(orderClient.getOrder()).thenReturn(Mono.just(Order.builder().serviceOrderId("orderId").build()));
        when(userClient.getUser()).thenReturn(Mono.empty());

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipWith();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isNull();
                    assertThat(serviceRecord.getOrderNumber()).isEqualTo("zipWithIsNotPerform");
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zipWhen(){
        when(orderClient.getOrder())
                .thenReturn(Mono.just(Order.builder().orderNumber("orderNumber").serviceOrderId("orderId").build()));
        when(userClient.getUserByOrderNumber("orderNumber"))
                .thenReturn(Mono.just(User.builder().dealerId("dealerId").build()));

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipWhen();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isEqualTo("orderId");
                    assertThat(serviceRecord.getDealerId()).isEqualTo("dealerId");
                }).verifyComplete();
    }

    @Test
    void should_return_right_service_record_when_use_zipWhen_empty_mono(){
        when(orderClient.getOrder())
                .thenReturn(Mono.just(Order.builder().orderNumber("orderNumber").serviceOrderId("orderId").build()));
        when(userClient.getUserByOrderNumber("orderNumber"))
                .thenReturn(Mono.empty());

        Mono<ServiceRecord> serviceRecordMono = orderService.useZipWhen();

        StepVerifier.create(serviceRecordMono)
                .consumeNextWith(serviceRecord -> {
                    assertThat(serviceRecord.getServiceOrderId()).isNull();
                    assertThat(serviceRecord.getOrderNumber()).isEqualTo("mapIsNotPerform");
                }).verifyComplete();
    }

    @Test
    void should_return_right_user_when_use_flatMap(){
        when(orderClient.getOrder())
                .thenReturn(Mono.just(Order.builder().orderNumber("orderNumber").build()));
        when(userClient.getUserByOrderNumber("orderNumber"))
                .thenReturn(Mono.just(User.builder().dealerId("dealerId").build()));

        Mono<User> userMono = orderService.useFlatMap();

        StepVerifier.create(userMono)
                .consumeNextWith(user -> {
                    assertThat(user.getDealerId()).isEqualTo("dealerId");
                }).verifyComplete();
    }

    @Test
    void should_return_right_user_when_use_flatMap_and_mono_is_empty(){
        when(orderClient.getOrder())
                .thenReturn(Mono.empty());
        when(userClient.getUserByOrderNumber("switchIfEmpty"))
                .thenReturn(Mono.just(User.builder().dealerId("switchIfEmpty").build()));

        Mono<User> userMono = orderService.useFlatMap();

        StepVerifier.create(userMono)
                .consumeNextWith(user -> {
                    assertThat(user.getDealerId()).isEqualTo("switchIfEmpty");
                }).verifyComplete();
    }
}