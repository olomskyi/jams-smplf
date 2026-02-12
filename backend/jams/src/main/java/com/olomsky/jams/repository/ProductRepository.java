package com.olomsky.jams.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.olomsky.jams.model.Product;

public interface  ProductRepository extends MongoRepository<Product, String>{
    
}
