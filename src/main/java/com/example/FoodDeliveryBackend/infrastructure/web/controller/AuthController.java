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

        Roles role = mapRole(currentUserProvider.getAuthorities().toString());


        return new MeResponse(
                currentUserProvider.getSubject(),
                currentUserProvider.getUsername(),
                currentUserProvider.getAuthorities().toString(),
                currentUserProvider.getEmail(),
                role
        );

    }

    private Roles mapRole(String authorities){

        return switch (authorities) {
            case "[ROLE_CUSTOMER]" -> Roles.CUSTOMER;
            case "[ROLE_RESTAURANT_ADMIN]" -> Roles.RESTAURANT_ADMIN;
            case "[ROLE_ADMIN]" -> Roles.ADMIN;
            case "[ROLE_DRIVER]" -> Roles.DRIVER;
            default -> null;

        };

    }

}
