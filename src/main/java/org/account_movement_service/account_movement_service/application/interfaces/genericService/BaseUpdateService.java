package org.account_movement_service.account_movement_service.application.interfaces.genericService;

import reactor.core.publisher.Mono;

public interface BaseUpdateService<T> {

    Mono<T> update(Long id, T data);

}
