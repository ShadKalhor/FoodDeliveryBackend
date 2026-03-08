package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
public class DriverDomain {

    private UUID id;
    private UUID userId;
    private VehicleType vehicleType;
    private boolean isOnline;
    @Embedded
    private GeoPoint currentLocation;
    private Instant lastLocationAt;
    private BigDecimal avgRating;

}
