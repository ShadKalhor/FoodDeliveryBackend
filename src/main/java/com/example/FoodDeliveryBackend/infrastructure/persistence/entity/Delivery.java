package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.DeliveryStatus;
import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Delivery")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private Order order;
    @ManyToOne
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "pickupLat")),
            @AttributeOverride(name = "lng", column = @Column(name = "pickupLng"))
    })
    private GeoPoint pickupLocation;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "lat", column = @Column(name = "dropoffLat")),
            @AttributeOverride(name = "lng", column = @Column(name = "dropoffLng"))
    })
    private GeoPoint dropOffLocation;

    private Instant assignedAt;
    private Instant acceptedAt;
    private Instant pickedUpAt;
    private Instant deliveredAt;



}
