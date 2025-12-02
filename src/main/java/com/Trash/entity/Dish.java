package com.Trash.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    // either a URL or a base64 string - keep it varchar/text in DB
    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Double price;

    // Many dishes belong to one admin
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Constructors
    public Dish() {}

    public Dish(String dishName, String imageUrl, Double price, Admin admin) {
        this.dishName = dishName;
        this.imageUrl = imageUrl;
        this.price = price;
        this.admin = admin;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
}
