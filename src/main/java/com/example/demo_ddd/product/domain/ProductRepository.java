package com.example.demo_ddd.product.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(String id);
    List<Product> findByCategory(String category);
    List<Product> findByActiveTrue();
    void deleteById(String id);
    boolean existsById(String id);
}
