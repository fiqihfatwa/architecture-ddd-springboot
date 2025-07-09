package com.example.demo_ddd.order.presentation;

import com.example.demo_ddd.order.application.OrderService;
import com.example.demo_ddd.order.domain.Order;
import com.example.demo_ddd.order.domain.valueobjects.OrderStatus;
import com.example.demo_ddd.order.presentation.dto.AddItemRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestParam String customerId) {
        try {
            String orderId = orderService.createOrder(customerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable String id) {
        Optional<Order> order = orderService.getOrder(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable String customerId) {
        List<Order> orders = orderService.getCustomerOrders(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<String> addItem(@PathVariable String id,
                                          @RequestBody AddItemRequest request) {
        try {
            orderService.addItemToOrder(id, request.getProductId(), request.getQuantity());
            return ResponseEntity.ok("Item added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/items/{productId}")
    public ResponseEntity<String> removeItem(@PathVariable String id,
                                             @PathVariable String productId) {
        try {
            orderService.removeItemFromOrder(id, productId);
            return ResponseEntity.ok("Item removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<String> confirmOrder(@PathVariable String id) {
        try {
            orderService.confirmOrder(id);
            return ResponseEntity.ok("Order confirmed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/ship")
    public ResponseEntity<String> shipOrder(@PathVariable String id) {
        try {
            orderService.shipOrder(id);
            return ResponseEntity.ok("Order shipped successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/deliver")
    public ResponseEntity<String> deliverOrder(@PathVariable String id) {
        try {
            orderService.deliverOrder(id);
            return ResponseEntity.ok("Order delivered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable String id) {
        try {
            orderService.cancelOrder(id);
            return ResponseEntity.ok("Order cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
