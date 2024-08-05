package org.account_movement_service.account_movement_service.application.interfaces.genericService;

import reactor.core.publisher.Mono;

public interface BaseGetInfoService<T> {
    Mono<T> getInfo(Long id);
}
