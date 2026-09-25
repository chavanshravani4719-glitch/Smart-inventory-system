package com.Shravani.inventory_system.repository;

import com.Shravani.inventory_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}