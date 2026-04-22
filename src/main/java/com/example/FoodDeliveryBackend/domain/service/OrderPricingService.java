package com.example.FoodDeliveryBackend.domain.service;

import com.example.FoodDeliveryBackend.domain.model.OrderItemDomain;
import com.example.FoodDeliveryBackend.domain.model.Pricing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderPricingService {

    public Pricing calculatePricing(List<OrderItemDomain> items){
        BigDecimal subtotal = items.stream()
                .map(OrderItemDomain::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Pricing pricing = new Pricing();

        pricing.setItemsSubtotal(subtotal);
        pricing.setDeliveryFee(BigDecimal.valueOf(3000));

        pricing.setServiceFee(subtotal.multiply(new BigDecimal("0.05")));
        pricing.setTax(subtotal.multiply(new BigDecimal("0.10")));

        pricing.setTotal(
                roundToNearest250(
                        subtotal
                                .add(pricing.getDeliveryFee())
                                .add(pricing.getServiceFee())
                                .add(pricing.getTax())
                )
        );

        return pricing;

    }

    private BigDecimal roundToNearest250(BigDecimal amount){
        BigDecimal unit = BigDecimal.valueOf(250);

        return amount
                .divide(unit, 0, RoundingMode.HALF_UP)
                .multiply(unit);
    }


}
