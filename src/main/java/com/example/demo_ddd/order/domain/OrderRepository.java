package com.example.demo_ddd.order.domain;

import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(String id);
    List<Order> findByCustomerId(String customerId);
    List<Order> findByStatus(OrderStatus status);
    void deleteById(String id);
}
