package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.OrderStatus;
import com.example.FoodDeliveryBackend.domain.enums.PaymentStatus;
import com.example.FoodDeliveryBackend.domain.model.Pricing;
import com.example.FoodDeliveryBackend.domain.model.RestaurantDomain;
import com.example.FoodDeliveryBackend.domain.model.Timestamps;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Order")
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User customer;

    @OneToOne
    private Restaurant restaurant;
    @OneToOne
    private CustomerAddress deliveryAddress;
    private OrderStatus status;
    @Embedded
    private Pricing pricing;
    @Embedded
    private Timestamps timestamps;

    @Enumerated
    private PaymentStatus paymentStatus;


}
