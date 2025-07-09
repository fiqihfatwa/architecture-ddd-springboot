package com.example.demo_ddd.customer.domain.valueobjects;

import com.example.demo_ddd.shared.domain.ValueObject;

public class Address extends ValueObject {
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;

    public Address(String street, String city, String state, String zipCode, String country) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        this.street = street.trim();
        this.city = city.trim();
        this.state = state != null ? state.trim() : "";
        this.zipCode = zipCode != null ? zipCode.trim() : "";
        this.country = country != null ? country.trim() : "";
    }

    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getZipCode() { return zipCode; }
    public String getCountry() { return country; }

    @Override
    public String toString() {
        return street + ", " + city +
                (state.isEmpty() ? "" : ", " + state) +
                (zipCode.isEmpty() ? "" : " " + zipCode) +
                (country.isEmpty() ? "" : ", " + country);
    }
}
