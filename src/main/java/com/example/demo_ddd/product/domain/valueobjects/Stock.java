package com.example.demo_ddd.product.domain.valueobjects;

import com.example.demo_ddd.shared.domain.ValueObject;

public class Stock extends ValueObject {
    private final int quantity;

    public Stock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity; }
    public boolean isAvailable() { return quantity > 0; }
    public boolean canFulfill(int requestedQuantity) {
        return quantity >= requestedQuantity;
    }

    public Stock reduce(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot reduce stock below zero");
        }
        return new Stock(quantity - amount);
    }

    public Stock increase(int amount) {
        return new Stock(quantity + amount);
    }

    @Override
    public String toString() { return String.valueOf(quantity); }
}
