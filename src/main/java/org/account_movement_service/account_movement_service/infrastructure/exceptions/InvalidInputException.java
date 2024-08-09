package org.account_movement_service.account_movement_service.infrastructure.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
