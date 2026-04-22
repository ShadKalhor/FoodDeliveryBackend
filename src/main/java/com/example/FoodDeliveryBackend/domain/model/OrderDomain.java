package com.example.FoodDeliveryBackend.domain.model;

import com.example.FoodDeliveryBackend.domain.enums.OrderStatus;
import com.example.FoodDeliveryBackend.domain.enums.PaymentStatus;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDomain {

    private UUID id;
    private UUID customerId;
    private UUID restaurantId;
    private UUID deliveryAddressId;
    private OrderStatus status;
    @Embedded
    private Pricing pricing;
    @Embedded
    private Timestamps timestamps;

    private List<OrderItemDomain> items;

    private PaymentStatus paymentStatus;


}
