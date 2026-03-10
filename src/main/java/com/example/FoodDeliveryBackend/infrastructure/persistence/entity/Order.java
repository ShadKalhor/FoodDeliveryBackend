package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import com.example.FoodDeliveryBackend.domain.enums.OrderStatus;
import com.example.FoodDeliveryBackend.domain.enums.PaymentStatus;
import com.example.FoodDeliveryBackend.domain.model.Pricing;

import com.example.FoodDeliveryBackend.domain.model.Timestamps;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "Orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private Restaurant restaurant;
    @ManyToOne
    private CustomerAddress deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Embedded
    private Pricing pricing;
    @Embedded
    private Timestamps timestamps;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


}
