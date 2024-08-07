package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.DeleteMovementService;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteMovementImp implements DeleteMovementService {

    private final MovementRepository movementRepository;

    @Override
    public Mono<Void> delete(Long id) {
        return movementRepository.findById(id).flatMap(m -> movementRepository.deleteById(id));
    }
}
