package com.example.demo_ddd.product.presentation.dto;

import java.math.BigDecimal;

public class CreateProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String currency;
    private String category;
    private int initialStock;

    // Constructors
    public CreateProductRequest() {}

    public CreateProductRequest(String name, String description, BigDecimal price,
                                String currency, String category, int initialStock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.category = category;
        this.initialStock = initialStock;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getInitialStock() { return initialStock; }
    public void setInitialStock(int initialStock) { this.initialStock = initialStock; }
}
