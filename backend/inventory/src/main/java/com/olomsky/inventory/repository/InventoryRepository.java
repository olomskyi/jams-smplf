package com.olomsky.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olomsky.inventory.model.Inventory;

public interface InventoryRepository extends  JpaRepository<Inventory, Long> {
    
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
}
