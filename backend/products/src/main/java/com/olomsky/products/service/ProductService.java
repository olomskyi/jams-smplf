package com.olomsky.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.olomsky.products.dto.ProductRequest;
import com.olomsky.products.dto.ProductResponse;
import com.olomsky.products.model.Product;
import com.olomsky.products.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepo;

    public ProductResponse createProduct(ProductRequest productDTO) {
        Product product = Product.builder()
            .name(productDTO.name())
            .description(productDTO.description())
            .price(productDTO.price())
            .build();
        productRepo.save(product);

        log.info("Product created successfully");
        return  new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = productRepo.findAll()
            .stream()
            .map(product -> new ProductResponse(
                product.getId(), product.getName(), product.getDescription(), product.getPrice()))
            .toList();

        log.info("Products (" + products.size() + ") successfully received");
        return products;
    }
}
