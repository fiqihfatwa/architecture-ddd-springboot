package com.example.demo_ddd.order.domain;

import com.example.demo_ddd.order.domain.events.OrderCreatedEvent;
import com.example.demo_ddd.order.domain.events.OrderStatusChangedEvent;
import com.example.demo_ddd.order.domain.valueobjects.OrderItem;
import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;
import com.example.demo_ddd.shared.domain.DomainEvent;
import com.example.demo_ddd.shared.domain.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class Order extends Entity {
    private String customerId;
    private String customerName;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private String shippingAddress;
    private List<DomainEvent> domainEvents;

    // For MongoDB
    protected Order() {
        super();
        this.items = new ArrayList<>();
        this.domainEvents = new ArrayList<>();
    }

    public Order(String customerId, String customerName, String shippingAddress) {
        this();
        this.customerId = customerId;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.PENDING;
        this.totalAmount = BigDecimal.ZERO;
    }

    public void addItem(OrderItem item) {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot add items to non-pending order");
        }

        this.items.add(item);
        calculateTotal();
        updateTimestamp();
    }

    public void removeItem(String productId) {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Cannot remove items from non-pending order");
        }

        items.removeIf(item -> item.getProductId().equals(productId));
        calculateTotal();
        updateTimestamp();
    }

    public void confirm() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Can only confirm pending orders");
        }
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot confirm empty order");
        }

        OrderStatus oldStatus = this.status;
        this.status = OrderStatus.CONFIRMED;
        updateTimestamp();

        addDomainEvent(new OrderCreatedEvent(this.id, customerId, totalAmount.toString()));
        addDomainEvent(new OrderStatusChangedEvent(this.id, oldStatus, status));
    }

    public void ship() {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Can only ship confirmed orders");
        }

        OrderStatus oldStatus = this.status;
        this.status = OrderStatus.SHIPPED;
        updateTimestamp();

        addDomainEvent(new OrderStatusChangedEvent(this.id, oldStatus, status));
    }

    public void deliver() {
        if (status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Can only deliver shipped orders");
        }

        OrderStatus oldStatus = this.status;
        this.status = OrderStatus.DELIVERED;
        updateTimestamp();

        addDomainEvent(new OrderStatusChangedEvent(this.id, oldStatus, status));
    }

    public void cancel() {
        if (status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel delivered orders");
        }

        OrderStatus oldStatus = this.status;
        this.status = OrderStatus.CANCELLED;
        updateTimestamp();

        addDomainEvent(new OrderStatusChangedEvent(this.id, oldStatus, status));
    }

    private void calculateTotal() {
        this.totalAmount = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void addDomainEvent(DomainEvent event) {
        this.domainEvents.add(event);
    }

    public List<DomainEvent> getDomainEvents() {
        return new ArrayList<>(domainEvents);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    // Getters
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public OrderStatus getStatus() { return status; }
    public String getShippingAddress() { return shippingAddress; }
    public int getItemCount() { return items.size(); }
}
