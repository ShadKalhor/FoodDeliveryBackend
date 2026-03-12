package com.example.FoodDeliveryBackend.infrastructure.web.advice;

import com.example.FoodDeliveryBackend.domain.exception.StructuredError;
import lombok.Getter;

import static com.example.FoodDeliveryBackend.infrastructure.web.advice.ErrorTypeToHttpStatusMapper.httpStatus;

@Getter
public class ErrorStructureException extends RuntimeException {


    private final int httpStatus;

    private final String message;

    public ErrorStructureException(StructuredError structuredError) {
        this.httpStatus = httpStatus(structuredError.type());
        this.message = structuredError.message();
    }
}