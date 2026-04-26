package com.example.FoodDeliveryBackend.domain.driverCommands;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.domain.model.DriverDomain;
import com.example.FoodDeliveryBackend.domain.port.out.DriverRepositoryPort;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class AddDriverCommand {

    private final DriverRepositoryPort driverRepositoryPort;


    public Either<StructuredError, Output> execute(Input input){
        return driverRepositoryPort.save(input.toParams()).map(Output::of);
    }


    @Value
    public static class Input{

        UUID accountId;
        VehicleType vehicleType;

        private DriverDomain toParams() {
            return new DriverDomain(UUID.randomUUID(), accountId,vehicleType,
                    true,null,
                    null,BigDecimal.ZERO);
        }

    }

    @Value
    public static class Output{

        UUID id;
        UUID accountId;
        VehicleType vehicleType;
        BigDecimal avgRating;

        static Output of(DriverDomain domain) {
            return new Output(domain.getId(), domain.getUserId(),
                    domain.getVehicleType(), domain.getAvgRating());
        }


    }

}


