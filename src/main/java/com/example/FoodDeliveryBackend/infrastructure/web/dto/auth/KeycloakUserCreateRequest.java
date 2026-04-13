package com.example.FoodDeliveryBackend.infrastructure.web.dto.auth;

public record KeycloakUserCreateRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        boolean emailVerified
) {
}
