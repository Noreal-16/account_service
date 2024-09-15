package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.imp.movementImp.dto.DepositDTO;
import org.account_movement_service.account_movement_service.application.imp.movementImp.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.imp.movementImp.dto.ResReportDto;
import org.account_movement_service.account_movement_service.application.imp.movementImp.dto.TransferDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface MovementService {

    Mono<MovementDTO> makeTransfer(TransferDTO transferDTO);

    Mono<MovementDTO> makeDeposit(DepositDTO depositDTO);

    Flux<ResReportDto> getReport(Long accountId, LocalDate startDate, LocalDate endDate);
}
