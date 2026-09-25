package com.Shravani.inventory_system.repository;

import com.Shravani.inventory_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}