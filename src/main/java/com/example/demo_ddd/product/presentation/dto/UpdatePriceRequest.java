package com.example.demo_ddd.product.presentation.dto;

import java.math.BigDecimal;

public class UpdatePriceRequest {
    private BigDecimal price;
    private String currency;

    public UpdatePriceRequest() {}

    public UpdatePriceRequest(BigDecimal price, String currency) {
        this.price = price;
        this.currency = currency;
    }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
