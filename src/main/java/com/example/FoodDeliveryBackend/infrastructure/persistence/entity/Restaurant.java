package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "Restaurant")
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
