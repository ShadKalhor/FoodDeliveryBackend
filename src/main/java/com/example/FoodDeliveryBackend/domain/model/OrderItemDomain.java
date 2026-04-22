package com.example.FoodDeliveryBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDomain {
    private UUID id;
    private UUID orderId;
    private UUID menuItemId;
    private String nameSnapshot;
    private BigDecimal unitPriceSnapshot;
    private int quantity;
    private BigDecimal lineTotal;
}
