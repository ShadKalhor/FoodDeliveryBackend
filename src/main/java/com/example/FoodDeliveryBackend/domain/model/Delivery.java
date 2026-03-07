package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.DeliveryStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
public class Delivery {

    private UUID id;
    private UUID orderId;
    private UUID driverId;
    private DeliveryStatus status;
    private GeoPoint pickupLocation;
    private GeoPoint dropOffLocation;

    private Instant assignedAt;
    private Instant acceptedAt;
    private Instant pickedUpAt;
    private Instant deliveredAt;

}
