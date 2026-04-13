package com.example.FoodDeliveryBackend.infrastructure.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AdminTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}