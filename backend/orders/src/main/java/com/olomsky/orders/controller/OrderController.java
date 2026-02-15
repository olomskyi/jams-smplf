package com.olomsky.orders.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.olomsky.orders.dto.OrderRequest;
import com.olomsky.orders.dto.OrderResponse;
import com.olomsky.orders.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@RestController
public class OrderController {
    
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponse> getOrders() {
        log.info("Request handling: {}", Thread.currentThread());
        return orderService.getOrders();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String setOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Processing POST request with entity: {}", orderRequest);
        orderService.setOrder(orderRequest);
        return "Order created successfully";
    }
}
