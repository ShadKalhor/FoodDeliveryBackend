package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.MenuCategories;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
public class MenuItemDomain {
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
