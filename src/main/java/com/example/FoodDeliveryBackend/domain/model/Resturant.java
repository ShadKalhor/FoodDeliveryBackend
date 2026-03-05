package com.example.FoodDeliveryBackend.domain.model;


import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;
import java.util.UUID;


@Setter
@Getter
public class Resturant {

    private UUID id;
    private String name;
    private String description;
    private UUID ownerUserId;
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
