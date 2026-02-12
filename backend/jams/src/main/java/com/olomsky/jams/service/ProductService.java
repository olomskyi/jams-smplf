package com.olomsky.jams.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olomsky.jams.dto.ProductRequest;
import com.olomsky.jams.dto.ProductResponse;
import com.olomsky.jams.model.Product;
import com.olomsky.jams.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepo;

    public void createProduct(ProductRequest productDTO) {
        Product product = Product.builder()
            .name(productDTO.name())
            .description(productDTO.description())
            .price(productDTO.price())
            .build();
        productRepo.save(product);

        log.info("Product created successfully");
    }

    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll()
            .stream()
            .map(product -> new ProductResponse(
                product.getId(), product.getName(), product.getDescription(), product.getPrice()))
            .toList();
    }
}
