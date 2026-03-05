package com.example.FoodDeliveryBackend.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
public class MenuItem {
    private UUID id;
    private UUID resturantId;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;
    private MenuCategories category;
    private String imageUrl;
    private Instant createdAt;
}
