package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.movementDto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.ResReportDto;
import org.account_movement_service.account_movement_service.application.dto.movementDto.TransferDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface MovementService {

    Mono<MovementDTO> makeTransfer(TransferDTO transferDTO);

    Mono<MovementDTO> makeDeposit(DepositDTO depositDTO);

    Flux<ResReportDto> getReport(Long accountId, LocalDate startDate, LocalDate endDate);
}
