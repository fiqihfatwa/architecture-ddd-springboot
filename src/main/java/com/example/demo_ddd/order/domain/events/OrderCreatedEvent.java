package com.example.demo_ddd.order.domain.events;

import com.example.demo_ddd.shared.domain.DomainEvent;

public class OrderCreatedEvent extends DomainEvent {
    private final String customerId;
    private final String totalAmount;

    public OrderCreatedEvent(String aggregateId, String customerId, String totalAmount) {
        super(aggregateId);
        this.customerId = customerId;
        this.totalAmount = totalAmount;
    }

    public String getCustomerId() { return customerId; }
    public String getTotalAmount() { return totalAmount; }
}
