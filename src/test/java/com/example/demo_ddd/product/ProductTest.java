package com.example.demo_ddd.product;

import com.example.demo_ddd.product.domain.Product;
import com.example.demo_ddd.product.domain.valueobjects.Price;
import com.example.demo_ddd.product.domain.valueobjects.ProductName;
import com.example.demo_ddd.product.domain.valueobjects.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    @Test
    public void shouldCreateProductSuccessfully() {
        // Given
        ProductName name = new ProductName("Test Product");
        String description = "Test Description";
        Price price = new Price(new BigDecimal("100.00"), "USD");
        String category = "Test Category";
        Stock stock = new Stock(10);

        // When
        Product product = new Product(name, description, price, category, stock);

        // Then
        assertNotNull(product);
        assertEquals(name, product.getName());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(category, product.getCategory());
        assertEquals(stock, product.getStock());
        assertTrue(product.isActive());
        assertTrue(product.isAvailable());
    }

    @Test
    public void shouldReduceStockSuccessfully() {
        // Given
        Product product = createTestProduct();

        // When
        product.reduceStock(3);

        // Then
        assertEquals(7, product.getStock().getQuantity());
    }

    @Test
    public void shouldThrowExceptionWhenReducingMoreThanAvailableStock() {
        // Given
        Product product = createTestProduct();

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> product.reduceStock(15));
    }

    @Test
    public void shouldDeactivateProduct() {
        Product product = createTestProduct();
        product.deactivate();
        assertFalse(product.isActive());
        assertFalse(product.isAvailable());
    }

    @Test
    public void shouldIncreaseStockSuccessfully() {
        Product product = createTestProduct();
        product.increaseStock(5);
        assertEquals(15, product.getStock().getQuantity());
    }

    private Product createTestProduct() {
        ProductName name = new ProductName("Test Product");
        String description = "Test Description";
        Price price = new Price(new BigDecimal("100.00"), "USD");
        String category = "Test Category";
        Stock stock = new Stock(10);

        return new Product(name, description, price, category, stock);
    }
}
