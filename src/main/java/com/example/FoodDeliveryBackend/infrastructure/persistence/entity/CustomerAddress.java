package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.model.GeoPoint;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="CustomerAddress")
public class CustomerAddress {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User user;
    private String label;
    private String address;
    @Embedded
    private GeoPoint location;

}

