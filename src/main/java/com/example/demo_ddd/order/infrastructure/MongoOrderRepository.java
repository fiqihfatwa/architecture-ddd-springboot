package com.example.demo_ddd.order.infrastructure;

import com.example.demo_ddd.order.domain.Order;
import com.example.demo_ddd.order.domain.OrderRepository;
import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoOrderRepository extends MongoRepository<Order, String>, OrderRepository {
    List<Order> findByCustomerId(String customerId);
    List<Order> findByStatus(OrderStatus status);
}
