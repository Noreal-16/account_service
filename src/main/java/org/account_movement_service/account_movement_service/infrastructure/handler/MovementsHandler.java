package org.account_movement_service.account_movement_service.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.movementDto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.TransferDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetReportMovementService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeDepositService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeTransferService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovementsHandler {

    private final MakeDepositService makeDepositService;
    private final MakeTransferService makeTransferService;
    private final GetReportMovementService getReportMovementService;

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

    public Mono<ServerResponse> reportMovement(ServerRequest request) {
        Long customerId = Long.parseLong(request.pathVariable("customer-id"));
        String startDateStr = request.queryParam("startDate").orElseThrow(() -> new IllegalArgumentException("Start date is required"));
        String endDateStr = request.queryParam("endDate").orElseThrow(() -> new IllegalArgumentException("End date is required"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);
        return getReportMovementService.getReport(customerId, startDate, endDate)
                .collectList()
                .flatMap(reportList -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(reportList));

    }

}
