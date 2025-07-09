package com.example.demo_ddd.product.domain;

import com.example.demo_ddd.product.domain.events.ProductCreatedEvent;
import com.example.demo_ddd.product.domain.events.StockReducedEvent;
import com.example.demo_ddd.product.domain.valueobjects.Price;
import com.example.demo_ddd.product.domain.valueobjects.ProductName;
import com.example.demo_ddd.product.domain.valueobjects.Stock;
import com.example.demo_ddd.shared.domain.DomainEvent;
import com.example.demo_ddd.shared.domain.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "products")
public class Product extends Entity {
    private ProductName name;
    private String description;
    private Price price;
    private String category;
    private Stock stock;
    private boolean active;
    private List<DomainEvent> domainEvents;

    // For MongoDB
    protected Product() {
        super();
        this.domainEvents = new ArrayList<>();
    }

    public Product(ProductName name, String description, Price price, String category, Stock stock) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.active = true;

        addDomainEvent(new ProductCreatedEvent(this.id, name.getValue(), category));
    }

    public void updatePrice(Price newPrice) {
        if (newPrice == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        this.price = newPrice;
        updateTimestamp();
    }

    public void reduceStock(int quantity) {
        if (!stock.canFulfill(quantity)) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        Stock oldStock = this.stock;
        this.stock = stock.reduce(quantity);
        updateTimestamp();

        addDomainEvent(new StockReducedEvent(this.id, quantity, stock.getQuantity()));
    }

    public void increaseStock(int quantity) {
        this.stock = stock.increase(quantity);
        updateTimestamp();
    }

    public void deactivate() {
        this.active = false;
        updateTimestamp();
    }

    public void activate() {
        this.active = true;
        updateTimestamp();
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
    public ProductName getName() { return name; }
    public String getDescription() { return description; }
    public Price getPrice() { return price; }
    public String getCategory() { return category; }
    public Stock getStock() { return stock; }
    public boolean isActive() { return active; }
    public boolean isAvailable() { return active && stock.isAvailable(); }
}
