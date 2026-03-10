package com.example.FoodDeliveryBackend.infrastructure.web.dto.auth;

import com.example.FoodDeliveryBackend.domain.enums.Roles;

import java.util.UUID;

public record AuthResponse(
        UUID userId,
        String name,
        String email,
        Roles role,
        String accessToken
) {
}
