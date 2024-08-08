package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import reactor.core.publisher.Mono;

public interface MakeDepositService {
    Mono<MovementDTO> makeDeposit(DepositDTO depositDTO);
}
