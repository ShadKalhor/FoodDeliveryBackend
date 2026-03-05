package com.example.FoodDeliveryBackend.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
public class Driver {

    private UUID id;
    private UUID userId;
    private VehicleType vehicleType;
    private boolean isOnline;
    private GeoPoint currentLocation;
    private GeoPoint lastLocationAt;
    private BigDecimal avgRating;

}
