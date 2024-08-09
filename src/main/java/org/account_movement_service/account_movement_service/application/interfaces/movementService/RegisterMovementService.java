package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import reactor.core.publisher.Mono;

public interface RegisterMovementService {

    Mono<MovementDTO> registerMovement(Long accountId, Double amount, Double newBalance);

}
