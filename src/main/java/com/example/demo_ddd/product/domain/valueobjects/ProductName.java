package com.example.demo_ddd.product.domain.valueobjects;

import com.example.demo_ddd.shared.domain.ValueObject;

public class ProductName extends ValueObject {
    private final String value;

    public ProductName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (value.length() > 100) {
            throw new IllegalArgumentException("Product name cannot exceed 100 characters");
        }
        this.value = value.trim();
    }

    public String getValue() { return value; }

    @Override
    public String toString() { return value; }
}
