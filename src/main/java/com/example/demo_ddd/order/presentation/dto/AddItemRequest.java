package com.example.demo_ddd.order.presentation.dto;

public class AddItemRequest {
    private String productId;
    private int quantity;

    public AddItemRequest() {}

    public AddItemRequest(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
