package com.example.FoodDeliveryBackend.domain.port.out;

import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.OrderItemDomain;
import io.vavr.control.Either;

public interface OrderItemRepositoryPort {

    Either<StructuredError, OrderItemDomain> save(OrderItemDomain domain);

}
