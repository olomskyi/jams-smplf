package com.olomsky.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.olomsky.inventory.model.Inventory;

import java.util.Optional;

public interface InventoryRepository extends  JpaRepository<Inventory, Long> {

    boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);

    Optional<Inventory> findBySkuCode(String skuCode);
}
