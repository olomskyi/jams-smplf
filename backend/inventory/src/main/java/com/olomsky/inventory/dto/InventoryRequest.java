package com.olomsky.inventory.dto;

public record InventoryRequest (
        String skuCode,
        Integer quantity){}
