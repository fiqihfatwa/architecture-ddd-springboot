package com.example.demo_ddd.customer;

import com.example.demo_ddd.customer.domain.Customer;
import com.example.demo_ddd.customer.domain.valueobjects.Address;
import com.example.demo_ddd.customer.domain.valueobjects.CustomerName;
import com.example.demo_ddd.customer.domain.valueobjects.Email;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void shouldCreateCustomerSuccessfully() {
        // Given
        CustomerName name = new CustomerName("John", "Doe");
        Email email = new Email("john.doe@example.com");
        String phone = "08123456789";
        Address address = new Address("Street 1", "City", "State", "12345", "Country");

        // When
        Customer customer = new Customer(name, email, phone, address);

        // Then
        assertNotNull(customer);
        assertEquals(name, customer.getName());
        assertEquals(email, customer.getEmail());
        assertEquals(phone, customer.getPhone());
        assertEquals(address, customer.getAddress());
        assertTrue(customer.isActive());
    }

    @Test
    public void shouldUpdateCustomerAddressSuccessfully() {
        // Given
        Customer customer = createTestCustomer();
        Address newAddress = new Address("New Street", "New City", "New State", "99999", "New Country");

        // When
        customer.updateAddress(newAddress);

        // Then
        assertEquals(newAddress, customer.getAddress());
    }

    @Test
    public void shouldUpdateCustomerPhoneSuccessfully() {
        // Given
        Customer customer = createTestCustomer();
        String newPhone = "08987654321";

        // When
        customer.updatePhone(newPhone);

        // Then
        assertEquals(newPhone, customer.getPhone());
    }

    @Test
    public void shouldThrowExceptionWhenEmailIsInvalid() {
        // Then
        assertThrows(IllegalArgumentException.class, () -> new Email("invalid-email"));
    }

    @Test
    public void shouldThrowExceptionWhenFirstNameIsEmpty() {
        // Then
        assertThrows(IllegalArgumentException.class, () -> new CustomerName("", "Doe"));
    }

    private Customer createTestCustomer() {
        CustomerName name = new CustomerName("John", "Doe");
        Email email = new Email("john.doe@example.com");
        String phone = "08123456789";
        Address address = new Address("Street 1", "City", "State", "12345", "Country");

        return new Customer(name, email, phone, address);
    }
}
