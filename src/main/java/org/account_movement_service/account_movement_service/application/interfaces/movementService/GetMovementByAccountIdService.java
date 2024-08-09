package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import reactor.core.publisher.Mono;

public interface GetMovementByAccountIdService {
    Mono<MovementDTO> getMovementByAccountId(Long movementId);
}
