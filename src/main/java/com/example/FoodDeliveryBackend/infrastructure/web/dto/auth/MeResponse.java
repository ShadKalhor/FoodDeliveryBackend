package com.example.FoodDeliveryBackend.infrastructure.web.dto.auth;

import com.example.FoodDeliveryBackend.domain.enums.Roles;

import java.util.UUID;

public record MeResponse(
        String userId,
        String name,
        String phone,
        String email,
        Roles role
) {
}