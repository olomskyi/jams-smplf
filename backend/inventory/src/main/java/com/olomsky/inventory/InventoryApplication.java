package com.olomsky.inventory;

import com.olomsky.inventory.model.Inventory;
import com.olomsky.inventory.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
	}

    @Bean
    CommandLineRunner init(InventoryRepository repo) {

        // Insert test data after context init
        return args -> {
            if (repo.count() == 0) {
                Inventory inv = new Inventory();
                inv.setSkuCode("macbook_pro_17");
                inv.setQuantity(55);
                repo.save(inv);
            }
        };
    }
}
