package com.example.FoodDeliveryBackend.domain.port.out;

import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.DriverDomain;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;

public interface DriverRepositoryPort {

    Either<StructuredError, DriverDomain> save(DriverDomain driverDomain);

    Either<StructuredError, DriverDomain> update(DriverDomain driverDomain);

    Either<StructuredError, DriverDomain> findById(UUID driverId);

    List<DriverDomain> findAll();

    Either<StructuredError, Void> deleteById(UUID driverId);



}
