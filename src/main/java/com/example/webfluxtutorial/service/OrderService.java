package com.example.webfluxtutorial.service;

import com.example.webfluxtutorial.client.OrderClient;
import com.example.webfluxtutorial.client.UserClient;
import com.example.webfluxtutorial.controller.dto.Order;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.controller.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderClient orderClient;

    private final UserClient userClient;

    public Mono<List<ServiceRecord>> getAllOrders() {
        return orderClient.getAllOrders().switchIfEmpty(Mono.just(Collections.emptyList()));
    }

    public Mono<ServiceRecord> useZip() {
        Mono<Order> order = orderClient.getOrder();
        Mono<User> user = userClient.getUser();
        return Mono.zip(order, user)
                .map(objects -> {
                    Order orderDto = objects.getT1();
                    User userDto = objects.getT2();
                    return ServiceRecord.builder()
                            .serviceOrderId(orderDto.getServiceOrderId())
                            .dealerId(userDto.getDealerId())
                            .build();
                });
    }

    public Mono<ServiceRecord> useZipAndMapIsNotPerformWhenGetMonoIsEmpty() {
        Mono<Order> order = orderClient.getEmptyOrder();
        Mono<User> user = userClient.getEmptyUser();
        return Mono.zip(order, user)
                .map(objects -> {
                    Order orderDto = objects.getT1();
                    User userDto = objects.getT2();
                    if (userDto.getDealerId() == null) {
                        return ServiceRecord.builder()
                                .serviceOrderId(orderDto.getServiceOrderId())
                                .build();
                    }
                    return ServiceRecord.builder()
                            .dealerId(userDto.getDealerId())
                            .build();
                }).switchIfEmpty(Mono.just(ServiceRecord.builder().orderNumber("mapIsNotPerform").build()));
    }

    public Mono<ServiceRecord> useZipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault() {
        Mono<Order> order = orderClient.getEmptyOrder()
                .defaultIfEmpty(Order.builder().serviceOrderId("tt").build());
        Mono<User> user = userClient.getEmptyUser()
                .switchIfEmpty(Mono.just(User.builder().build()));
        return Mono.zip(order, user)
                .map(objects -> {
                    Order orderDto = objects.getT1();
                    User userDto = objects.getT2();
                    if (userDto.getDealerId() == null) {
                        return ServiceRecord.builder()
                                .serviceOrderId(orderDto.getServiceOrderId())
                                .build();
                    }
                    return ServiceRecord.builder()
                            .serviceOrderId(orderDto.getServiceOrderId())
                            .dealerId(userDto.getDealerId())
                            .build();
                })
                .switchIfEmpty(Mono.just(ServiceRecord.builder().orderNumber("mapIsNotPerform").build()));
    }

    public Mono<ServiceRecord> useZipWhenGetMonoIsOptional() {
        Mono<Optional<Order>> optionalOrder = orderClient.getOptionalOrder();
        Mono<Optional<User>> optionalEmptyUser = userClient.getOptionalEmptyUser();
        return Mono.zip(optionalOrder, optionalEmptyUser)
                .map(objects -> {
                    Optional<Order> orderOptional = objects.getT1();
                    Optional<User> userOptional = objects.getT2();
                    if (userOptional.isPresent() && orderOptional.isPresent()) {
                        return ServiceRecord.builder()
                                .serviceOrderId(orderOptional.get().getServiceOrderId())
                                .dealerId(userOptional.get().getDealerId())
                                .build();
                    }
                    return ServiceRecord.builder()
                            .build();
                })
                .switchIfEmpty(Mono.just(ServiceRecord.builder().orderNumber("mapIsNotPerform").build()));
    }

    public Mono<ServiceRecord> useZipWith() {
        Mono<Order> order = orderClient.getOrder();
        Mono<User> user = userClient.getUser();
        return order.zipWith(user)
                .map(objects -> {
                    Order orderDto = objects.getT1();
                    User userDto = objects.getT2();
                    return ServiceRecord.builder()
                            .serviceOrderId(orderDto.getServiceOrderId())
                            .dealerId(userDto.getDealerId())
                            .build();
                })
                .switchIfEmpty(Mono.just(ServiceRecord.builder().orderNumber("zipWithIsNotPerform").build()));
    }

    public Mono<ServiceRecord> useZipWhen() {
        return orderClient.getOrder()
                .zipWhen(order -> userClient.getUserByOrderNumber(order.getOrderNumber()))
                .map(objects -> {
                    Order orderDto = objects.getT1();
                    User userDto = objects.getT2();
                    return ServiceRecord.builder()
                            .serviceOrderId(orderDto.getServiceOrderId())
                            .dealerId(userDto.getDealerId())
                            .build();
                })
                .switchIfEmpty(Mono.just(ServiceRecord.builder().orderNumber("mapIsNotPerform").build()));
    }

    public Mono<User> useFlatMap() {
        return orderClient.getOrder()
                .switchIfEmpty(Mono.just(Order.builder().orderNumber("switchIfEmpty").build()))
                .flatMap(order -> userClient.getUserByOrderNumber(order.getOrderNumber()));
    }

    public Mono<User> useMap() {
        return orderClient.getOrder()
                .map(order -> {
                    order.setServiceOrderId("anySet");
                    return User.builder().dealerId("useMap").build();
                });
    }

    public Mono<Order> useDoOnNext() {
        return orderClient.getOrder()
                .doOnNext(order -> order.setServiceOrderId("doOnNext"));
    }

    public Mono<Void> useMonoVoid() {
        return orderClient.getOrder()
                .flatMap(order -> {
                    if (Objects.equals(order.getOrderNumber(), "orderNumber")) {
                        return orderClient.deleteOrderByOrderNumber("orderNumber");
                    }
                    return Mono.empty();
                });

    }

    public Mono<Void> useMonoVoidWithThen() {
        return orderClient.getOrder()
                .flatMap(order -> orderClient.updateOrderByOrderNumber(order.getOrderNumber()))
                .then();
    }

    public Mono<Order> useThenReturn() {
        return orderClient.deleteOrderByOrderNumber("any")
                .thenReturn(Order.builder().orderNumber("return").build());
    }

    public Mono<Order> useMonoFilter() {
        return orderClient.getOrder()
                .filter(order -> !isEmpty(order.getOrderNumber()));
    }

    public Mono<List<Order>> useFluxFromIterable() {
        return orderClient.getAllOrders()
                .flatMap(serviceRecords -> Flux.fromIterable(serviceRecords)
                        .flatMap(serviceRecord -> {
                            String orderNumber = serviceRecord.getOrderNumber();
                            return orderClient.deleteOrderByOrderNumber(orderNumber)
                                    .thenReturn(Order.builder().orderNumber(orderNumber).build());
                        }).collectList()
                );
    }

    public Mono<Optional<Order>> getOptionalOrder() {
        return Mono.just(Optional.of(Order.builder().orderNumber("ooo").build()));
    }
}
