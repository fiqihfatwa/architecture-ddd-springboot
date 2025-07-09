package com.example.demo_ddd.product.domain.valueobjects;

import com.example.demo_ddd.shared.domain.ValueObject;

import java.math.BigDecimal;

public class Price extends ValueObject {
    private final BigDecimal amount;
    private final String currency;

    public Price(BigDecimal amount, String currency) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price amount cannot be null or negative");
        }
        if (currency == null || currency.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency cannot be null or empty");
        }
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
