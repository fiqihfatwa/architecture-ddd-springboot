package com.example.demo_ddd.customer.domain;

import com.example.demo_ddd.customer.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(String id);
    Optional<Customer> findByEmail(String email);
    boolean existsByEmail(String email);
    void deleteById(String id);
}
