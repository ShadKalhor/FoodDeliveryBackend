package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.DeliveryStatus;
import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "delivery")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {

    @Id
    private UUID id;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "driver_id")
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


    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }

}
