package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.controller.dto.Order;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.controller.dto.User;
import com.example.webfluxtutorial.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
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

    @GetMapping(value = "/useOnErrorResume")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<ServiceRecord>> getAllOrders() {
        return orderService.getAllOrders()
                .doOnNext(item -> {
                    log.info("orders is: {}", item);
                })
                .onErrorResume(error -> {
                    log.error("no orders find");
                    return Mono.just(Collections.emptyList());
                });
    }

    @GetMapping(value = "/zip")
    public Mono<ServiceRecord> useZip(){
        return orderService.useZip();
    }

    @GetMapping(value = "/zipAndMapIsNotPerformWhenGetMonoIsEmpty")
    public Mono<ServiceRecord> useZipAndMapIsNotPerformWhenGetMonoIsEmpty(){
        return orderService.useZipAndMapIsNotPerformWhenGetMonoIsEmpty();
    }

    @GetMapping(value = "/zipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault")
    public Mono<ServiceRecord> useZipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault(){
        return orderService.useZipAndMapIsPerformWhenGetMonoIsEmptyButHasDefault();
    }

    @GetMapping(value = "/zipWhenGetMonoIsOptional")
    public Mono<ServiceRecord> useZipWhenGetMonoIsOptional(){
        return orderService.useZipWhenGetMonoIsOptional();
    }

    @GetMapping(value = "/zipWith")
    public Mono<ServiceRecord> useZipWith(){
        return orderService.useZipWith();
    }

    @GetMapping(value = "/zipWhen")
    public Mono<ServiceRecord> useZipWhen(){
        return orderService.useZipWhen();
    }

    @GetMapping(value = "/flatMap")
    public Mono<User> useFlatMap(){
        return orderService.useFlatMap();
    }

    @GetMapping(value = "/map")
    public Mono<User> useMap(){
        return orderService.useMap();
    }

    @GetMapping(value = "/doOnNext")
    public Mono<Order> useDoOnNext(){
        return orderService.useDoOnNext();
    }

    @GetMapping(value = "/monoVoid")
    public Mono<Void>  useMonoVoid() {
        return orderService.useMonoVoid();
    }

    @GetMapping(value = "/monoVoidWithThen")
    public Mono<Void>  useMonoVoidWithThen() {
        return orderService.useMonoVoidWithThen();
    }

    @GetMapping(value = "/monoThenReturn")
    public Mono<Order> useMonoThenReturn(){
        return orderService.useThenReturn();
    }

    @GetMapping(value = "/monoFilter")
    public Mono<Order> useMonoFilter(){
        return orderService.useMonoFilter();
    }

    @GetMapping(value = "/fluxFromIterable")
    public Mono<List<Order>> useFluxFromIterable(){
        return orderService.useFluxFromIterable();
    }
}
