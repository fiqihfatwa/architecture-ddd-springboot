package com.example.demo_ddd.order.domain.events;

import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;
import com.example.demo_ddd.shared.domain.DomainEvent;

public class OrderStatusChangedEvent extends DomainEvent {
    private final OrderStatus oldStatus;
    private final OrderStatus newStatus;

    public OrderStatusChangedEvent(String aggregateId, OrderStatus oldStatus, OrderStatus newStatus) {
        super(aggregateId);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public OrderStatus getOldStatus() { return oldStatus; }
    public OrderStatus getNewStatus() { return newStatus; }
}
