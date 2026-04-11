package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name="customer_address")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddress {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account user;
    private String label;
    private String address;
    @Embedded
    private GeoPoint location;


    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }
}

