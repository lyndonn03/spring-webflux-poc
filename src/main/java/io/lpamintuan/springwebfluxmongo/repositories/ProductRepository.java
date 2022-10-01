package io.lpamintuan.springwebfluxmongo.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import io.lpamintuan.springwebfluxmongo.models.Product;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
    
}
