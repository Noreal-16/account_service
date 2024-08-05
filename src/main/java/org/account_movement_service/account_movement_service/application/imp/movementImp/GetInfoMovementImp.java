package org.account_movement_service.account_movement_service.application.imp.movementImp;

import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetInfoMovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetInfoMovementImp implements GetInfoMovementService {
    @Override
    public Mono<MovementDTO> getInfo(Long id) {
        return null;
    }
}
