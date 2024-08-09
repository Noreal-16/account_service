package org.account_movement_service.account_movement_service.application.interfaces.genericService;

import reactor.core.publisher.Mono;

public interface BaseRegisterService<T,R> {
    Mono<T> register(R data);
}
