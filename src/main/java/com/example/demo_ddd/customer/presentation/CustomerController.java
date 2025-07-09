package com.example.demo_ddd.customer.presentation;

import com.example.demo_ddd.customer.application.CustomerService;
import com.example.demo_ddd.customer.domain.Customer;
import com.example.demo_ddd.customer.presentation.dto.RegisterCustomerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> registerCustomer(@RequestBody RegisterCustomerRequest request) {
        try {
            String customerId = customerService.registerCustomer(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getPhone(),
                    request.getStreet(),
                    request.getCity(),
                    request.getState(),
                    request.getZipCode(),
                    request.getCountry()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(customerId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable String id) {
        Optional<Customer> customer = customerService.getCustomer(id);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);
        return customer.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/phone")
    public ResponseEntity<String> updatePhone(@PathVariable String id,
                                              @RequestParam String phone) {
        try {
            customerService.updateCustomerPhone(id, phone);
            return ResponseEntity.ok("Phone updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
