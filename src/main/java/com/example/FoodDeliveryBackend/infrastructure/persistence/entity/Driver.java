package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "Driver")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private boolean isOnline;
    @Embedded
    private GeoPoint currentLocation;
    private Instant lastLocationAt;
    private BigDecimal avgRating;


}
