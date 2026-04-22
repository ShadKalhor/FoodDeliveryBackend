package com.example.FoodDeliveryBackend.domain.port.out;

import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.OrderDomain;
import io.vavr.control.Either;

import java.util.UUID;

public interface OrderRepositoryPort {

    Either<StructuredError, OrderDomain> save(OrderDomain domain);

    Either<StructuredError, OrderDomain> findById(UUID orderId);

}
