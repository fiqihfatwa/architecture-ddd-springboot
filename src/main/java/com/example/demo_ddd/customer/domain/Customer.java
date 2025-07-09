package com.example.demo_ddd.customer.domain;

import com.example.demo_ddd.customer.domain.events.CustomerRegisteredEvent;
import com.example.demo_ddd.customer.domain.valueobjects.Address;
import com.example.demo_ddd.customer.domain.valueobjects.CustomerName;
import com.example.demo_ddd.customer.domain.valueobjects.Email;
import com.example.demo_ddd.shared.domain.DomainEvent;
import com.example.demo_ddd.shared.domain.Entity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "customers")
public class Customer extends Entity {
    private CustomerName name;
    private Email email;
    private String phone;
    private Address address;
    private boolean active;
    private List<DomainEvent> domainEvents;

    // For MongoDB
    protected Customer() {
        super();
        this.domainEvents = new ArrayList<>();
    }

    public Customer(CustomerName name, Email email, String phone, Address address) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.active = true;

        addDomainEvent(new CustomerRegisteredEvent(this.id, name.getFullName(), email.getValue()));
    }

    public void updateAddress(Address newAddress) {
        this.address = newAddress;
        updateTimestamp();
    }

    public void updatePhone(String newPhone) {
        this.phone = newPhone;
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
    public CustomerName getName() { return name; }
    public Email getEmail() { return email; }
    public String getPhone() { return phone; }
    public Address getAddress() { return address; }
    public boolean isActive() { return active; }
}
