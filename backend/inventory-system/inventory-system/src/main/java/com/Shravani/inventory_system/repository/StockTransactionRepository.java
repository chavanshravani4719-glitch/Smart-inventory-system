package com.Shravani.inventory_system.repository;

import com.Shravani.inventory_system.entity.TransactionStatus;
import com.Shravani.inventory_system.entity.StockTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByProductId(Long productId);

    List<StockTransaction> findByStatus(TransactionStatus status);

    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'IN' THEN t.quantity ELSE 0 END), 0) - " +
            "COALESCE(SUM(CASE WHEN t.type = 'OUT' AND t.status = 'APPROVED' THEN t.quantity ELSE 0 END), 0) " +
            "FROM StockTransaction t WHERE t.product.id = :productId")
    Integer getCurrentStock(@Param("productId") Long productId);
}
