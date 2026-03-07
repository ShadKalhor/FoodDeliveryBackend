package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.OrderStatus;
import com.example.FoodDeliveryBackend.domain.enums.PaymentStatus;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
public class Order {

    private UUID id;
    private UUID customerId;
    private UUID resturantId;
    private UUID deliveryAddressId;
    private OrderStatus status;
    @Embedded
    private Pricing pricing;
    @Embedded
    private Timestamps timestamps;

    private PaymentStatus paymentStatus;


}
