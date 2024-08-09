package org.account_movement_service.account_movement_service.infrastructure.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseCustomer {
    private String errorCode;
    private String errorMessage;

    @Override
    public String toString() {
        return "ErrorResponseCustomer{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

}
