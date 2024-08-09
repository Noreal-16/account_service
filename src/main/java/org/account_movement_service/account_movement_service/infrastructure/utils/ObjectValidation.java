package org.account_movement_service.account_movement_service.infrastructure.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.infrastructure.exceptions.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ObjectValidation {
    private final Validator validator;

    @SneakyThrows
    public <T> T validate(T object) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);
        if (constraintViolations.isEmpty()) return object;
        else {
            String message = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
            throw new CustomException(message, HttpStatus.BAD_REQUEST);
        }
    }
}
