package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "driver")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Driver {

    @Id
    private UUID id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Account user;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    private boolean isOnline;
    @Embedded
    private GeoPoint currentLocation;
    private Instant lastLocationAt;
    private BigDecimal avgRating;



    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}
