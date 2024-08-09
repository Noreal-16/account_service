package org.account_movement_service.account_movement_service.infrastructure.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class CustomWebExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status;
        String message;

        if (ex instanceof ResponseStatusException statusException) {
            status = (HttpStatus) statusException.getStatusCode();
            message = statusException.getReason();
        } else if (ex instanceof CustomException customException) {
            status = customException.getHttpStatus();
            message = customException.getMessage();
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = "An unexpected error occurred.";
        }

        ErrorResponseCustomer errorResponse = new ErrorResponseCustomer(status.name(), message);
        byte[] errorResponseBytes;
        try {
            errorResponseBytes = objectMapper.writeValueAsBytes(errorResponse);
        } catch (Exception e) {
            errorResponseBytes = new byte[0];
        }

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(errorResponseBytes))
                        .doOnNext(buffer -> exchange.getResponse().setStatusCode(status))
                        .doOnNext(buffer -> exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON))
        );
    }
}
