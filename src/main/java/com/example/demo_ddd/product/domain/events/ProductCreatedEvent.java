package com.example.demo_ddd.product.domain.events;

import com.example.demo_ddd.shared.domain.DomainEvent;

public class ProductCreatedEvent extends DomainEvent {
    private final String productName;
    private final String category;

    public ProductCreatedEvent(String aggregateId, String productName, String category) {
        super(aggregateId);
        this.productName = productName;
        this.category = category;
    }

    public String getProductName() { return productName; }
    public String getCategory() { return category; }
}