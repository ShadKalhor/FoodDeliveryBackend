package com.example.FoodDeliveryBackend.domain.exception;

public record StructuredError(String message, ErrorType type) {
}
