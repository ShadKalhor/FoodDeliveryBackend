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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Account customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    @ManyToOne
    @JoinColumn(name = "delivery_address_id")
    private CustomerAddress deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Embedded
    private Pricing pricing;
    @Embedded
    private Timestamps timestamps;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    @PrePersist
    public void prePersist() {
        if (id == null) id = UUID.randomUUID();
    }

}
