package com.olomsky.inventory.service;

import com.olomsky.inventory.dto.InventoryResponse;
import com.olomsky.inventory.model.Inventory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.olomsky.inventory.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode, Integer quantity) {
        log.info("isInStock: SKU='{}', Quantity={}", skuCode, quantity);
        boolean exists = inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
        log.info("Exists: {}", exists);

        if (!exists) {
            Optional<Inventory> found = inventoryRepository.findBySkuCode(skuCode);
            if (found.isPresent()) {
                log.info("Found present: {}", found.toString());
            }
            else {
                log.info("Not Found !");
            }
        }

        return exists;
    }

    public List<InventoryResponse> getAllData() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> new InventoryResponse(
                        inventory.getSkuCode(), inventory.getQuantity()))
                .toList();
    }
}
