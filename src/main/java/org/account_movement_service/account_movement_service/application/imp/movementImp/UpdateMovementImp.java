package org.account_movement_service.account_movement_service.application.imp.movementImp;

import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.UpdateMovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateMovementImp implements UpdateMovementService {
    @Override
    public Mono<MovementDTO> update(Long id, MovementDTO data) {
        return null;
    }
}
