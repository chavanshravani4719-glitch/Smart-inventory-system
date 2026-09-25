package com.Shravani.inventory_system.service;

import com.Shravani.inventory_system.entity.*;
import com.Shravani.inventory_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockTransactionRepository stockTransactionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public StockTransaction recordStockIn(Long productId, int quantity, String reason, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StockTransaction txn = new StockTransaction(product, TransactionType.IN, quantity, reason, user);
        return stockTransactionRepository.save(txn);
    }

    public StockTransaction recordStockOut(Long productId, int quantity, String reason, Long userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StockTransaction txn = new StockTransaction(product, TransactionType.OUT, quantity, reason, user);
        return stockTransactionRepository.save(txn);
    }

    public List<StockTransaction> getTransactionHistory(Long productId) {
        return stockTransactionRepository.findByProductId(productId);
    }

    public StockTransaction approveTransaction(Long transactionId) {
        StockTransaction txn = stockTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        txn.setStatus(TransactionStatus.APPROVED);
        return stockTransactionRepository.save(txn);
    }

    public StockTransaction rejectTransaction(Long transactionId) {
        StockTransaction txn = stockTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        txn.setStatus(TransactionStatus.REJECTED);
        return stockTransactionRepository.save(txn);
    }

    public List<StockTransaction> getPendingTransactions() {
        return stockTransactionRepository.findByStatus(TransactionStatus.PENDING);
    }
}