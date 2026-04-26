package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import com.example.FoodDeliveryBackend.domain.accountCommands.AddAccountCommand;
import com.example.FoodDeliveryBackend.domain.enums.Roles;
import com.example.FoodDeliveryBackend.infrastructure.security.AuthService;
import com.example.FoodDeliveryBackend.infrastructure.security.RegistrationService;
import com.example.FoodDeliveryBackend.infrastructure.security.CurrentUserProvider;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.LoginRequest;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.LoginResponse;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.MeResponse;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RegistrationService registrationService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody AddAccountCommand.Input request) {
        registrationService.registerCustomer(request);
        return ResponseEntity.ok("User registered successfully");
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


    @GetMapping("/hello")
    public String publicHello() {
        return "public ok";
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
