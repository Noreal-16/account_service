package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.TransferDTO;
import reactor.core.publisher.Mono;

public interface RegisterMovementService {

    Mono<MovementDTO> makeDeposit(DepositDTO depositDTO);

    Mono<MovementDTO> makeTransfer(TransferDTO transferDTO);

}
