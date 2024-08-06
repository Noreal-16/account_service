package org.account_movement_service.account_movement_service.infrastructure.handler.interfaces;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface BaseHandler {

    Mono<ServerResponse> getAllHandler(ServerRequest serverRequest);

    Mono<ServerResponse> getInfoHandler(ServerRequest serverRequest);

    Mono<ServerResponse> registerHandler(ServerRequest serverRequest);

    Mono<ServerResponse> updateHandler(ServerRequest serverRequest);

    Mono<ServerResponse> deleteHandler(ServerRequest serverRequest);

}
