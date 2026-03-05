package com.example.FoodDeliveryBackend.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
public class CustomerAddress {

    private UUID id;
    private UUID userId;
    private String label;
    private String address;
    private GeoPoint location;

}
