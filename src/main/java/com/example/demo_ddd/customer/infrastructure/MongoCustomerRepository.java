package com.example.demo_ddd.customer.infrastructure;

import com.example.demo_ddd.customer.domain.Customer;
import com.example.demo_ddd.customer.domain.CustomerRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MongoCustomerRepository extends MongoRepository<Customer, String>, CustomerRepository {
    @Query("{'email.value': ?0}")
    Optional<Customer> findByEmail(String email);

    @Query(value = "{'email.value': ?0}", exists = true)
    boolean existsByEmail(String email);
}
