package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
public class DriverDomain {

    private UUID id;
    private UUID userId;
    private VehicleType vehicleType;
    private boolean isOnline;
    private GeoPoint currentLocation;
    private GeoPoint lastLocationAt;
    private BigDecimal avgRating;

}
