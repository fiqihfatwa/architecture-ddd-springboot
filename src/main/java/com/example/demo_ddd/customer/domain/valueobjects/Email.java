package com.example.demo_ddd.customer.domain.valueobjects;

import com.example.demo_ddd.shared.domain.ValueObject;

import java.util.regex.Pattern;

public class Email extends ValueObject {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    public Email(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.value = value.toLowerCase().trim();
    }

    public String getValue() { return value; }

    @Override
    public String toString() { return value; }
}
