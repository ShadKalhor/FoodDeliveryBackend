package com.example.FoodDeliveryBackend.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Timestamps {

    private Instant createdAt;
    private Instant acceptedAt;
    private Instant readyAt;
    private Instant pickedUpAt;
    private Instant deliveredAt;
    private Instant cancelledAt;

    public Timestamps(Instant createdAt){
        this.createdAt = createdAt;
    }

}
