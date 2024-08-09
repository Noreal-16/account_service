package org.account_movement_service.account_movement_service.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.TransferDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeDepositService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeTransferService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovementsHandler {

    private final MakeDepositService makeDepositService;
    private final MakeTransferService makeTransferService;

    public Mono<ServerResponse> makeDeposit(ServerRequest request) {
        Mono<DepositDTO> depositDTOMono = request.bodyToMono(DepositDTO.class);
        return depositDTOMono.flatMap(makeDepositService::makeDeposit)
                .flatMap(movementDTO -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(movementDTO), DepositDTO.class));
    }

    public Mono<ServerResponse> makeTransfer(ServerRequest request) {
        Mono<TransferDTO> transferDTOMono = request.bodyToMono(TransferDTO.class);
        return transferDTOMono.flatMap(makeTransferService::makeTransfer)
                .flatMap(movementDTO -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(movementDTO), TransferDTO.class));
    }

}
