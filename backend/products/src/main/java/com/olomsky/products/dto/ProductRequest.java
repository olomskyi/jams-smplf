package com.olomsky.products.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductRequest(
    String id,
    String name,
    String description,
    String skuCode,
    @Schema(type = "number", example = "99.99")
    BigDecimal price
) {}
