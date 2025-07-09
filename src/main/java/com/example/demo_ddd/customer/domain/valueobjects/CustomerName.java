package com.example.demo_ddd.customer.domain.valueobjects;

import com.example.demo_ddd.shared.domain.ValueObject;

public class CustomerName extends ValueObject {
    private final String firstName;
    private final String lastName;

    public CustomerName(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }

    @Override
    public String toString() { return getFullName(); }
}
