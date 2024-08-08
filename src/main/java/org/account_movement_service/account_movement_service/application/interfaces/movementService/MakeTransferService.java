package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.TransferDTO;
import reactor.core.publisher.Mono;

public interface MakeTransferService {
    Mono<MovementDTO> makeTransfer(TransferDTO transferDTO);
}
