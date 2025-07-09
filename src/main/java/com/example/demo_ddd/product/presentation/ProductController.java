package com.example.demo_ddd.product.presentation;

import com.example.demo_ddd.product.application.ProductService;
import com.example.demo_ddd.product.domain.Product;
import com.example.demo_ddd.product.presentation.dto.CreateProductRequest;
import com.example.demo_ddd.product.presentation.dto.UpdatePriceRequest;
import com.example.demo_ddd.product.presentation.dto.UpdateStockRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest request) {
        try {
            String productId = productService.createProduct(
                    request.getName(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getCurrency(),
                    request.getCategory(),
                    request.getInitialStock()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(productId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {
        Optional<Product> product = productService.getProduct(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<String> updatePrice(@PathVariable String id,
                                              @RequestBody UpdatePriceRequest request) {
        try {
            productService.updateProductPrice(id, request.getPrice(), request.getCurrency());
            return ResponseEntity.ok("Price updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/stock/reduce")
    public ResponseEntity<String> reduceStock(@PathVariable String id,
                                              @RequestBody UpdateStockRequest request) {
        try {
            productService.reduceStock(id, request.getQuantity());
            return ResponseEntity.ok("Stock reduced successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/stock/increase")
    public ResponseEntity<String> increaseStock(@PathVariable String id,
                                                @RequestBody UpdateStockRequest request) {
        try {
            productService.increaseStock(id, request.getQuantity());
            return ResponseEntity.ok("Stock increased successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
