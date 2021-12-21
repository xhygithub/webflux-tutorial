package com.example.webfluxtutorial.controller;

import com.example.webfluxtutorial.controller.dto.Order;
import com.example.webfluxtutorial.controller.dto.ServiceRecord;
import com.example.webfluxtutorial.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = "date", produces = MediaType.APPLICATION_JSON_VALUE)
public class DateFormatController {

    private final OrderService orderService;

    @GetMapping(value = "/dateFormat")
    public Mono<List<ServiceRecord>> getOrderWithDate(){
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/optional")
    public Mono<Optional<Order>> getOptionalOrder(){
        return orderService.getOptionalOrder();
    }
}
