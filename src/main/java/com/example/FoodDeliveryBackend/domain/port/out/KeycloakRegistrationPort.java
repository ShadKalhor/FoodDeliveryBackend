package com.example.FoodDeliveryBackend.domain.port.out;

import com.example.FoodDeliveryBackend.domain.accountCommands.AddAccountCommand;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import io.vavr.control.Either;

public interface KeycloakRegistrationPort {

    Either<StructuredError, Void> registerCustomer(AddAccountCommand.Input input);


}
