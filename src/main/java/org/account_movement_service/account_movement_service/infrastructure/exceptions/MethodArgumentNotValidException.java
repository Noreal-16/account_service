package org.account_movement_service.account_movement_service.infrastructure.exceptions;

import org.springframework.validation.Errors;

public class MethodArgumentNotValidException extends Exception {
    public MethodArgumentNotValidException(String message) {
        super(message);
    }

    public Errors getBindingResult() {

        return null;
    }
}
