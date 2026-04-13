package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.usecase.RegisterDriverUseCase;
import com.example.FoodDeliveryBackend.infrastructure.web.advice.ErrorStructureException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/driver")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class DriverController {


    RegisterDriverUseCase registerDriverUseCase;


    @PostMapping("/new-driver")
    public DriverResponse PostNewDriver(RegisterDriverUseCase.RegisterDriverInput driverRequest){

        RegisterDriverUseCase.RegisterDriverOutput driverOutput = registerDriverUseCase.execute(driverRequest).getOrElseThrow(ErrorStructureException::new);
        return new DriverResponse(driverOutput.getDriverId(),driverOutput.getAccountId(),driverOutput.getVehicleType(), false,BigDecimal.ZERO);
    }


    @PostMapping("/online")
    public Void PostOnlineDriver(){return null;}

    @PostMapping("/offline")
    public Void PostOfflineDriver(){return null;}

    @PostMapping("/location")
    public Void PostDriverLocation(){return null;}

    @GetMapping("/deliveries/assigned")
    public Void GetAssignedDrivers(){return null;}

    @PostMapping("/deliveries/{id}/accept")
    public Void AcceptDelivery(){return null;}

    @PostMapping("/deliveries/{id}/picked-up")
    public Void PickedUpDelivery(){return null;}

    @PostMapping("/deliveries/{id}/delivered")
    public Void DeliveredDelivery(){return null;}


    @Value
    public static class DriverResponse{

        UUID id;
        UUID accountId;
        VehicleType vehicleType;
        boolean isOnline;
        BigDecimal avgRating;

    }


}
