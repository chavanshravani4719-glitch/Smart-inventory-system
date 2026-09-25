package com.Shravani.inventory_system.service;

import com.Shravani.inventory_system.entity.Product;
import com.Shravani.inventory_system.repository.ProductRepository;
import com.Shravani.inventory_system.repository.StockTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existing = getProductById(id);
        existing.setName(updatedProduct.getName());
        existing.setSku(updatedProduct.getSku());
        existing.setUnit(updatedProduct.getUnit());
        existing.setReorderLevel(updatedProduct.getReorderLevel());
        existing.setCategory(updatedProduct.getCategory());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public int getCurrentStock(Long productId) {
        Integer stock = stockTransactionRepository.getCurrentStock(productId);
        return stock != null ? stock : 0;
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findAll().stream()
                .filter(p -> getCurrentStock(p.getId()) <= p.getReorderLevel())
                .toList();
    }
}