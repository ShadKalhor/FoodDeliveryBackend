package com.example.FoodDeliveryBackend.infrastructure.web.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String email,
        String firstName,
        String lastName
) {
}