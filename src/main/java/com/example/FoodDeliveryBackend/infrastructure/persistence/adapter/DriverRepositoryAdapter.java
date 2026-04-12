package com.example.FoodDeliveryBackend.infrastructure.persistence.adapter;

import com.example.FoodDeliveryBackend.domain.exception.ErrorType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.DriverDomain;
import com.example.FoodDeliveryBackend.domain.port.out.DriverRepositoryPort;
import com.example.FoodDeliveryBackend.infrastructure.persistence.entity.Driver;
import com.example.FoodDeliveryBackend.infrastructure.persistence.mapper.DriverMapper;
import com.example.FoodDeliveryBackend.infrastructure.persistence.repository.DriverRepositoryJPA;
import io.vavr.control.Either;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class DriverRepositoryAdapter  implements DriverRepositoryPort {

    private final DriverRepositoryJPA driverRepositoryJPA;
    private final DriverMapper driverMapper;

    @Override
    public Either<StructuredError, DriverDomain> save(DriverDomain domain) {

        Driver entity = driverMapper.toEntity(domain);

        return Try.of(() ->driverRepositoryJPA.save(entity)).map(driverMapper::toDomain)
                .toEither(new StructuredError("Error While Saving Driver",
                        ErrorType.DATABASE_ERROR));
    }

    @Override
    public Either<StructuredError, DriverDomain> findById(UUID driverId) {

        return Try.of(() -> driverRepositoryJPA.findOptionById(driverId))
                .toEither(() -> new StructuredError("Error While Getting Driver", ErrorType.DATABASE_ERROR))
                .flatMap(option -> option
                        .map(driverMapper::toDomain)
                        .toEither(() -> new StructuredError("Error While Mapping The Returned Value of FindDriverById",
                                ErrorType.SERVER_ERROR))
                );
    }

    @Override
    public List<DriverDomain> findAll() {
        return driverRepositoryJPA.findAll().stream().map(driverMapper::toDomain).toList();
    }

    @Override
    public Either<StructuredError, Void> deleteById(UUID driverId) {
        return Try.run(() -> driverRepositoryJPA.deleteById(driverId)).toEither(new StructuredError("Error While Deleting Driver",
                ErrorType.DATABASE_ERROR));
    }


}
