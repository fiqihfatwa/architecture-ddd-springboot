package com.example.demo_ddd.order;

import com.example.demo_ddd.order.domain.Order;
import com.example.demo_ddd.order.domain.valueobjects.OrderItem;
import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    @Test
    public void shouldCreateOrderSuccessfully() {
        // Given
        String customerId = "customer123";
        String customerName = "John Doe";
        String shippingAddress = "123 Test Street";

        // When
        Order order = new Order(customerId, customerName, shippingAddress);

        // Then
        assertNotNull(order);
        assertEquals(customerId, order.getCustomerId());
        assertEquals(customerName, order.getCustomerName());
        assertEquals(shippingAddress, order.getShippingAddress());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(BigDecimal.ZERO, order.getTotalAmount());
        assertEquals(0, order.getItemCount());
    }

    @Test
    public void shouldAddItemSuccessfully() {
        // Given
        Order order = createTestOrder();
        OrderItem item = new OrderItem("product1", "Test Product", 2, new BigDecimal("50.00"));

        // When
        order.addItem(item);

        // Then
        assertEquals(1, order.getItemCount());
        assertEquals(new BigDecimal("100.00"), order.getTotalAmount());
    }

    @Test
    public void shouldConfirmOrderSuccessfully() {
        // Given
        Order order = createTestOrder();
        OrderItem item = new OrderItem("product1", "Test Product", 2, new BigDecimal("50.00"));
        order.addItem(item);

        // When
        order.confirm();

        // Then
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    public void shouldThrowExceptionWhenConfirmingEmptyOrder() {
        // Given
        Order order = createTestOrder();

        // When & Then
        assertThrows(IllegalStateException.class, order::confirm);
    }

    @Test
    public void shouldRemoveItemSuccessfully() {
        Order order = createTestOrder();
        OrderItem item = new OrderItem("product1", "Test Product", 2, new BigDecimal("50.00"));
        order.addItem(item);

        order.removeItem("product1");

        assertEquals(0, order.getItemCount());
        assertEquals(BigDecimal.ZERO, order.getTotalAmount());
    }

    @Test
    public void shouldChangeStatusToShippedAndDelivered() {
        Order order = createTestOrder();
        OrderItem item = new OrderItem("product1", "Product", 1, new BigDecimal("100"));
        order.addItem(item);
        order.confirm();

        order.ship();
        assertEquals(OrderStatus.SHIPPED, order.getStatus());

        order.deliver();
        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    public void shouldAddDomainEventOnConfirm() {
        Order order = createTestOrder();
        order.addItem(new OrderItem("product1", "Product", 1, new BigDecimal("100")));

        order.confirm();

        assertFalse(order.getDomainEvents().isEmpty());
    }

    private Order createTestOrder() {
        String customerId = "customer123";
        String customerName = "John Doe";
        String shippingAddress = "123 Test Street";

        return new Order(customerId, customerName, shippingAddress);
    }
}
