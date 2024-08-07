package org.account_movement_service.account_movement_service.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.*;
import org.account_movement_service.account_movement_service.infrastructure.handler.interfaces.BaseHandler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovementsHandler implements BaseHandler {

    private final RegisterMovementService registerMovementService;
    private final GetAllMovementService getAllMovementService;
    private final GetInfoMovementService getInfoMovementService;
    private final UpdateMovementService updateMovementService;
    private final DeleteMovementService deleteMovementService;

    @Override
    public Mono<ServerResponse> getAllHandler(ServerRequest serverRequest) {
        Flux<MovementDTO> movementDTOFlux = getAllMovementService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movementDTOFlux, MovementDTO.class);
    }

    @Override
    public Mono<ServerResponse> getInfoHandler(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<MovementDTO> movementDTOMono = getInfoMovementService.getInfo(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(movementDTOMono, MovementDTO.class);
    }

    @Override
    public Mono<ServerResponse> registerHandler(ServerRequest serverRequest) {
        Mono<MovementDTO> movementDTOMono = serverRequest.bodyToMono(MovementDTO.class);
        return null;
//        return movementDTOMono.flatMap(saveMovement -> ServerResponse
//                .ok().contentType(MediaType.APPLICATION_JSON)
//                .body(registerMovementService.register(saveMovement), MovementDTO.class));
    }

    @Override
    public Mono<ServerResponse> updateHandler(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<MovementDTO> movementDTOMono = serverRequest.bodyToMono(MovementDTO.class);
        return movementDTOMono.flatMap(updateMovement -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateMovementService.update(id, updateMovement), MovementDTO.class));
    }

    @Override
    public Mono<ServerResponse> deleteHandler(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deleteMovementService.delete(id), MovementDTO.class);
    }
}
