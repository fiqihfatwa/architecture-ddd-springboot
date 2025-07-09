package com.example.demo_ddd.customer.application;

import com.example.demo_ddd.customer.domain.Customer;
import com.example.demo_ddd.customer.domain.CustomerRepository;
import com.example.demo_ddd.customer.domain.valueobjects.Address;
import com.example.demo_ddd.customer.domain.valueobjects.CustomerName;
import com.example.demo_ddd.customer.domain.valueobjects.Email;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public String registerCustomer(String firstName, String lastName, String email, String phone,
                                   String street, String city, String state, String zipCode, String country) {

        if (customerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        CustomerName name = new CustomerName(firstName, lastName);
        Email customerEmail = new Email(email);
        Address address = new Address(street, city, state, zipCode, country);

        Customer customer = new Customer(name, customerEmail, phone, address);
        Customer savedCustomer = customerRepository.save(customer);

        return savedCustomer.getId();
    }

    public void updateCustomerAddress(String customerId, String street, String city,
                                      String state, String zipCode, String country) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Address newAddress = new Address(street, city, state, zipCode, country);
        customer.updateAddress(newAddress);
        customerRepository.save(customer);
    }

    public void updateCustomerPhone(String customerId, String phone) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        customer.updatePhone(phone);
        customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getCustomer(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}