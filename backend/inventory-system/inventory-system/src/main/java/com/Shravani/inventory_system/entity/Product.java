package com.Shravani.inventory_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 50)
    private String sku;

    @Column(length = 20)
    private String unit;

    @Column(name = "reorder_level", nullable = false)
    private Integer reorderLevel;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Constructors
    public Product() {
    }

    public Product(String name, String sku, String unit, Integer reorderLevel, Category category) {
        this.name = name;
        this.sku = sku;
        this.unit = unit;
        this.reorderLevel = reorderLevel;
        this.category = category;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}