package com.example.demo_ddd.customer.domain.events;

import com.example.demo_ddd.shared.domain.DomainEvent;

public class CustomerRegisteredEvent extends DomainEvent {
    private final String customerName;
    private final String email;

    public CustomerRegisteredEvent(String aggregateId, String customerName, String email) {
        super(aggregateId);
        this.customerName = customerName;
        this.email = email;
    }

    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
}
