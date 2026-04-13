package com.example.FoodDeliveryBackend.infrastructure.web.dto.auth;

public record KeycloakCredential(
        String type,
        String value,
        boolean temporary
) {
}
