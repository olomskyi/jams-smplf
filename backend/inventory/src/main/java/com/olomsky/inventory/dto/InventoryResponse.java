package com.olomsky.inventory.dto;

public record InventoryResponse (
        String skuCode,
        Integer quantity){}
