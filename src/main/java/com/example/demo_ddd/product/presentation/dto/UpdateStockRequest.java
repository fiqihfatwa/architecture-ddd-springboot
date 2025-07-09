package com.example.demo_ddd.product.presentation.dto;

public class UpdateStockRequest {
    private int quantity;

    public UpdateStockRequest() {}

    public UpdateStockRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
