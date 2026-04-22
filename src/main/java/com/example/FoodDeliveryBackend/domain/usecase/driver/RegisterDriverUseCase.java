package com.example.FoodDeliveryBackend.domain.usecase.driver;

import com.example.FoodDeliveryBackend.domain.accountCommands.AddAccountCommand;
import com.example.FoodDeliveryBackend.domain.driverCommands.AddDriverCommand;
import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.enums.VehicleType;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

@RequiredArgsConstructor
public class RegisterDriverUseCase {

    private final AddAccountCommand addAccountCommand;
    private final AddDriverCommand addDriverCommand;

    public Either<StructuredError, RegisterDriverOutput> execute(RegisterDriverInput input) {
        return addAccountCommand.execute(
                        new AddAccountCommand.SaveAccountInput(
                                input.getUsername(),
                                input.getFirstName(),
                                input.getLastName(),
                                input.getPhone(),
                                input.getEmail(),
                                input.getPassword(),
                                Roles.DRIVER
                        )
                )
                .flatMap(accountOutput ->
                        addDriverCommand.execute(
                                new AddDriverCommand.SaveDriverInput(
                                        accountOutput.getId(),
                                        input.getVehicleType()
                                )
                        ).map(driverOutput ->
                                RegisterDriverOutput.of(accountOutput, driverOutput)
                        )
                );
    }

    @Value
    public static class RegisterDriverInput {
        String username;
        String firstName;
        String lastName;
        String phone;
        String email;
        String password;
        VehicleType vehicleType;
    }

    @Value
    public static class RegisterDriverOutput {
        UUID accountId;
        UUID driverId;
        String username;
        String firstName;
        String lastName;
        String phone;
        String email;
        VehicleType vehicleType;

        public static RegisterDriverOutput of(
                AddAccountCommand.SaveAccountOutput account,
                AddDriverCommand.SaveDriverOutput driver
        ) {
            return new RegisterDriverOutput(
                    account.getId(),
                    driver.getId(),
                    account.getUsername(),
                    account.getFirstName(),
                    account.getLastName(),
                    account.getPhone(),
                    account.getEmail(),
                    driver.getVehicleType()
            );
        }
    }
}