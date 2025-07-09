package com.example.demo_ddd.product.domain.events;

import com.example.demo_ddd.shared.domain.DomainEvent;

public class StockReducedEvent extends DomainEvent {
    private final int reducedQuantity;
    private final int remainingStock;

    public StockReducedEvent(String aggregateId, int reducedQuantity, int remainingStock) {
        super(aggregateId);
        this.reducedQuantity = reducedQuantity;
        this.remainingStock = remainingStock;
    }

    public int getReducedQuantity() { return reducedQuantity; }
    public int getRemainingStock() { return remainingStock; }
}