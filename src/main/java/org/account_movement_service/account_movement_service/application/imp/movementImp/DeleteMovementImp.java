package org.account_movement_service.account_movement_service.application.imp.movementImp;

import org.account_movement_service.account_movement_service.application.interfaces.movementService.DeleteMovementService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteMovementImp implements DeleteMovementService {
    @Override
    public Mono<Void> delete(Long id) {
        return null;
    }
}
