package com.example.FoodDeliveryBackend.domain.port.out;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.RegisterRequest;
import io.vavr.control.Either;

public interface KeycloakRegistrationPort {

    Either<StructuredError, Void> registerCustomer(RegisterRequest request, Roles role);


}
