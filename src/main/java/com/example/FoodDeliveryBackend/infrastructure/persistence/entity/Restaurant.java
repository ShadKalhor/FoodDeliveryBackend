package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "Restaurant")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    @ManyToOne
    private User ownerUser;
    private String address;
    @Embedded
    private GeoPoint location;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean isOpen;
    private BigDecimal minOrderAmount;
    private int avgPrepTimeInMins;
    private Instant createdAt;

}
