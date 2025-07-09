package com.example.demo_ddd.product.application;

import com.example.demo_ddd.product.domain.Product;
import com.example.demo_ddd.product.domain.ProductRepository;
import com.example.demo_ddd.product.domain.valueobjects.Price;
import com.example.demo_ddd.product.domain.valueobjects.ProductName;
import com.example.demo_ddd.product.domain.valueobjects.Stock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public String createProduct(String name, String description, BigDecimal price,
                                String currency, String category, int initialStock) {

        ProductName productName = new ProductName(name);
        Price productPrice = new Price(price, currency);
        Stock stock = new Stock(initialStock);

        Product product = new Product(productName, description, productPrice, category, stock);
        Product savedProduct = productRepository.save(product);

        return savedProduct.getId();
    }

    public void updateProductPrice(String productId, BigDecimal newPrice, String currency) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        Price price = new Price(newPrice, currency);
        product.updatePrice(price);
        productRepository.save(product);
    }

    public void reduceStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.reduceStock(quantity);
        productRepository.save(product);
    }

    public void increaseStock(String productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        product.increaseStock(quantity);
        productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProduct(String productId) {
        return productRepository.findById(productId);
    }

    @Transactional(readOnly = true)
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
