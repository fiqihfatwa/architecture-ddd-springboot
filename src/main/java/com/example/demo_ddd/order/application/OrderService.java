package com.example.demo_ddd.order.application;

import com.example.demo_ddd.customer.domain.Customer;
import com.example.demo_ddd.customer.domain.CustomerRepository;
import com.example.demo_ddd.order.domain.Order;
import com.example.demo_ddd.order.domain.OrderRepository;
import com.example.demo_ddd.order.domain.valueobjects.OrderItem;
import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;
import com.example.demo_ddd.product.domain.Product;
import com.example.demo_ddd.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public String createOrder(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (!customer.isActive()) {
            throw new IllegalArgumentException("Customer is not active");
        }

        Order order = new Order(customerId, customer.getName().getFullName(),
                customer.getAddress().toString());
        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }

    public void addItemToOrder(String orderId, String productId, int quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (!product.isAvailable()) {
            throw new IllegalArgumentException("Product is not available");
        }

        if (!product.getStock().canFulfill(quantity)) {
            throw new IllegalArgumentException("Insufficient stock");
        }

        OrderItem item = new OrderItem(productId, product.getName().getValue(),
                quantity, product.getPrice().getAmount());
        order.addItem(item);
        orderRepository.save(order);
    }

    public void removeItemFromOrder(String orderId, String productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.removeItem(productId);
        orderRepository.save(order);
    }

    public void confirmOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Validate stock availability for all items
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));

            if (!product.getStock().canFulfill(item.getQuantity())) {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName().getValue());
            }
        }

        // Reduce stock for all items
        for (OrderItem item : order.getItems()) {
            Product product = productRepository.findById(item.getProductId()).get();
            product.reduceStock(item.getQuantity());
            productRepository.save(product);
        }

        order.confirm();
        orderRepository.save(order);
    }

    public void shipOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.ship();
        orderRepository.save(order);
    }

    public void deliverOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.deliver();
        orderRepository.save(order);
    }

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // If order is confirmed, restore stock
        if (order.getStatus() == OrderStatus.CONFIRMED) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + item.getProductId()));
                product.increaseStock(item.getQuantity());
                productRepository.save(product);
            }
        }

        order.cancel();
        orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Optional<Order> getOrder(String orderId) {
        return orderRepository.findById(orderId);
    }

    @Transactional(readOnly = true)
    public List<Order> getCustomerOrders(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
}
