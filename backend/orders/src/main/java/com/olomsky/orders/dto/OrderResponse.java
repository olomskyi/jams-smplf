package com.olomsky.orders.dto;

public record OrderResponse(
    Long id,
    String orderNumber,
    String skuCode,
    Double price,
    Integer quantity) {}
