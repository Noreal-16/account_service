package org.account_movement_service.account_movement_service.infrastructure.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.movementDto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.ResReportDto;
import org.account_movement_service.account_movement_service.application.dto.movementDto.TransferDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/movements", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovementController {

    private final MovementService movementService;


    @PostMapping("/deposit")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovementDTO> makeDeposit(@RequestBody @Validated DepositDTO data) {
        return movementService.makeDeposit(data);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MovementDTO> makeTransfer(@RequestBody @Validated TransferDTO data) {
        return movementService.makeTransfer(data);
    }

    @PostMapping("/reports/{customer-id}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ResReportDto> getReportMovement(@PathVariable("customer-id") Long accountId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return movementService.getReport(accountId, startDate, endDate);
    }


}
