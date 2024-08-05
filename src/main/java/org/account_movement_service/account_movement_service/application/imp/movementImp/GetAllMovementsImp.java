package org.account_movement_service.account_movement_service.application.imp.movementImp;

import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetAllMovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetAllMovementsImp implements GetAllMovementService {
    @Override
    public Flux<MovementDTO> getAll() {
        return null;
    }
}
