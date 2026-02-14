package com.olomsky.products.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.olomsky.products.model.Product;

public interface  ProductRepository extends MongoRepository<Product, String>{
    
}
