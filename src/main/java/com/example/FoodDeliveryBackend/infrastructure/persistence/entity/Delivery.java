package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.DeliveryStatus;
import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Delivery")
public class Delivery {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private Order order;
    @ManyToOne
    private Driver driver;
    private DeliveryStatus status;
    private GeoPoint pickupLocation;
    private GeoPoint dropOffLocation;

    private Instant assignedAt;
    private Instant acceptedAt;
    private Instant pickedUpAt;
    private Instant deliveredAt;



}
