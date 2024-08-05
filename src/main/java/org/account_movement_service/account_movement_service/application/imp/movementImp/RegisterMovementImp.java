package org.account_movement_service.account_movement_service.application.imp.movementImp;

import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.RegisterMovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class RegisterMovementImp implements RegisterMovementService {
    @Override
    public Mono<MovementDTO> register(MovementDTO data) {
        return null;
    }
}
