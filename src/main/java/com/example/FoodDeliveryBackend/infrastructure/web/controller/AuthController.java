package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.infrastructure.security.CurrentUserProvider;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.MeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final CurrentUserProvider currentUserProvider;

    public AuthController(CurrentUserProvider currentUserProvider){
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public MeResponse GetMe(){

        return new MeResponse(
                currentUserProvider.getSubject(),
                currentUserProvider.getUsername(),
                null,
                currentUserProvider.getEmail(),
                mapRole()
        );

    }

    private Roles mapRole(){

        return switch (currentUserProvider.getAuthorities().toString().toLowerCase()) {
            case "customer" -> Roles.CUSTOMER;
            case "restaurant_admin" -> Roles.RESTAURANT_ADMIN;
            case "admin" -> Roles.ADMIN;
            case "driver" -> Roles.DRIVER;
            default -> null;

        };

    }

}
