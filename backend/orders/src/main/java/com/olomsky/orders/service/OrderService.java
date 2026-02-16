package com.olomsky.orders.service;

import java.util.List;

import com.olomsky.orders.client.InventoryClient;
import org.springframework.stereotype.Service;

import com.olomsky.orders.dto.OrderRequest;
import com.olomsky.orders.dto.OrderResponse;
import com.olomsky.orders.model.Order;
import com.olomsky.orders.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public OrderResponse setOrder(OrderRequest orderRequest) {
        log.info("Setting order: " + orderRequest);

        boolean isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (isInStock) {
            Order order = new Order();
            order.setOrderNumber(orderRequest.orderNumber());
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
            return new OrderResponse(
                    order.getId(),
                    order.getOrderNumber(),
                    order.getSkuCode(),
                    order.getPrice(),
                    order.getQuantity());
        } else {
            throw new RuntimeException("Product with SkuCode " + orderRequest.skuCode() +
                    " and quantity " + orderRequest.quantity() + " is not in stock");
        }
    }

    public List<OrderResponse> getOrders() {
        List<OrderResponse> orders = orderRepository.findAll()
            .stream()
            .map(order -> new OrderResponse(
                order.getId(), order.getOrderNumber(), order.getSkuCode(),
                order.getPrice(), order.getQuantity()))
            .toList();

        log.info("Orders (" + orders.size() + ") successfully received");
        return orders;
    }
}
