package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.MenuCategories;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "menu_item")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;

    @Enumerated(EnumType.STRING)
    private MenuCategories category;
    private String imageUrl;
    private Instant createdAt;


    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
