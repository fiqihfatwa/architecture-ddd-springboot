package com.example.demo_ddd.product.infrastructure;

import com.example.demo_ddd.product.domain.Product;
import com.example.demo_ddd.product.domain.ProductRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoProductRepository extends MongoRepository<Product, String>, ProductRepository {
    List<Product> findByCategory(String category);
    List<Product> findByActiveTrue();

    @Query("{'name.value': ?0}")
    List<Product> findByProductName(String name);

    @Query("{'stock.quantity': {$gt: 0}, 'active': true}")
    List<Product> findAvailableProducts();
}
