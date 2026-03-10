package com.example.FoodDeliveryBackend.infrastructure.web.controller;

import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.AuthResponse;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.LoginRequest;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.MeResponse;
import com.example.FoodDeliveryBackend.infrastructure.web.dto.auth.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse Register(@Valid @RequestBody RegisterRequest request){return null;}

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse Login(@Valid @RequestBody LoginRequest request){return null;}

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public MeResponse GetMe(){return null;}

}
