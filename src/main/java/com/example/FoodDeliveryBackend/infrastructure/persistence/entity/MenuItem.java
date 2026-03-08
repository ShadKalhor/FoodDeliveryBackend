package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.MenuCategories;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "MenuItem")
public class MenuItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Restaurant restaurant;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private MenuCategories category;
    private String imageUrl;
    private Instant createdAt;

}
