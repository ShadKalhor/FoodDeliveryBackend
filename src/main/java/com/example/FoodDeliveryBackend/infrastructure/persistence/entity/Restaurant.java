package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "restaurant")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    private UUID id;
    private String name;
    private String description;
    private String address;
    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;
    @Embedded
    private GeoPoint location;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean isOpen;
    private BigDecimal minOrderAmount;
    private int avgPrepTimeInMins;
    private Instant createdAt;


    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
