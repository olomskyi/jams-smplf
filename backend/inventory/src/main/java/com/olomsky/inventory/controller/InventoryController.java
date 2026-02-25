package com.olomsky.inventory.controller;

import com.olomsky.inventory.dto.InventoryResponse;
import com.olomsky.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        log.info("Accepted request: skuCode {}, quantity {}", skuCode, quantity);
        return inventoryService.isInStock(skuCode, quantity);
    }

    @GetMapping("/get-all-data")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllData() {
        return inventoryService.getAllData();
    }
}
