package com.Shravani.inventory_system.controller;

import com.Shravani.inventory_system.entity.StockTransaction;
import com.Shravani.inventory_system.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/in")
    public StockTransaction stockIn(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());
        String reason = (String) request.get("reason");
        Long userId = Long.valueOf(request.get("userId").toString());

        return stockService.recordStockIn(productId, quantity, reason, userId);
    }

    @PostMapping("/out")
    public StockTransaction stockOut(@RequestBody Map<String, Object> request) {
        Long productId = Long.valueOf(request.get("productId").toString());
        int quantity = Integer.parseInt(request.get("quantity").toString());
        String reason = (String) request.get("reason");
        Long userId = Long.valueOf(request.get("userId").toString());

        return stockService.recordStockOut(productId, quantity, reason, userId);
    }

    @GetMapping("/history/{productId}")
    public List<StockTransaction> getHistory(@PathVariable Long productId) {
        return stockService.getTransactionHistory(productId);
    }

    @PutMapping("/approve/{id}")
    public StockTransaction approve(@PathVariable Long id) {
        return stockService.approveTransaction(id);
    }

    @PutMapping("/reject/{id}")
    public StockTransaction reject(@PathVariable Long id) {
        return stockService.rejectTransaction(id);
    }

    @GetMapping("/pending")
    public List<StockTransaction> getPending() {
        return stockService.getPendingTransactions();
    }
}
