package org.account_movement_service.account_movement_service.application.interfaces.genericService;

import reactor.core.publisher.Flux;

public interface BaseGetAllService<T> {

    Flux<T> getAll();

}
