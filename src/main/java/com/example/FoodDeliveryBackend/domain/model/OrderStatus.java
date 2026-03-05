package com.example.FoodDeliveryBackend.domain.model;

public enum OrderStatus {
    DRAFT,
    PLACED,
    RESTURANT_ACCEPTED,
    RESTURANT_REJECTED,
    PREPARING,
    READY_FOR_PICKUP,
    DRIVER_ASSIGNED,
    PICKED_UP,
    DELIVERED,
    CALCELLED,
    EXPIRED,
}
