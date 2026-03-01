package com.olomsky.orders.service;

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.util.List;

import com.olomsky.orders.client.InventoryClient;
import com.olomsky.orders.dto.OrderRequest;
import com.olomsky.orders.dto.OrderResponse;
import com.olomsky.orders.model.Order;
import com.olomsky.orders.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.olomsky.orders.event.OrderEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    public OrderResponse setOrder(OrderRequest orderRequest) {
        log.info("Setting order: " + orderRequest);

        boolean isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        String orderNumber = String.valueOf(System.currentTimeMillis());

        if (isInStock) {
            Order order = new Order();
            order.setOrderNumber(orderNumber);
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);

            // KAFKA MESSAGE
            OrderEvent orderEvent = new OrderEvent(
                    orderNumber,
                    orderRequest.skuCode(),
                    ByteBuffer.wrap(orderRequest.price().setScale(2, RoundingMode.HALF_UP).unscaledValue().toByteArray()),
                    orderRequest.quantity(),
                    orderRequest.userDetails().email(),
                    orderRequest.userDetails().firstName(),
                    orderRequest.userDetails().lastName());

            log.info("KAFKA: sending event = {}", orderEvent);
            kafkaTemplate.send("order-topic", orderEvent);

            // FOR TESTING
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

    public OrderResponse fallbackMethod(OrderRequest orderRequest, Throwable throwable) {
        log.warn("Fallback triggered for SKU: {}. Reason: {}",
                orderRequest.skuCode(), throwable.getMessage());
        throw new RuntimeException("Inventory service is unavailable. Please try again later. " +
                throwable.getMessage());
    }
}
