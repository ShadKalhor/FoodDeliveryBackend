package com.example.FoodDeliveryBackend.domain.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class GeoPoint {

    private BigDecimal lat;
    private BigDecimal lng;

}