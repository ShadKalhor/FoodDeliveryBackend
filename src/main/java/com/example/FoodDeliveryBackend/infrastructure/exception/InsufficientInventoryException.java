package com.example.FoodDeliveryBackend.infrastructure.exception;


public class InsufficientInventoryException extends RuntimeException {
    public InsufficientInventoryException(String lines) {//String lines is temp
        super("Insufficient inventory: " + lines);
    }
}
