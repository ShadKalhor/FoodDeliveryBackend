package com.example.FoodDeliveryBackend.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.awt.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "OrderItem")
public class OrderItem {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Order order;

    @ManyToOne
    private MenuItem menuItem;
    private String nameSnapshot;
    private BigDecimal unitPriceSnapshot;
    private int quantity;
    private BigDecimal lineTotal;


}
