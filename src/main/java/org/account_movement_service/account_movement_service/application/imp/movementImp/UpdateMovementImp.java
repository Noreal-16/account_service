package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.UpdateMovementService;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UpdateMovementImp implements UpdateMovementService {

    private final MovementRepository movementRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;

    @Override
    public Mono<MovementDTO> update(Long id, MovementDTO data) {
        return movementRepository.findById(id).flatMap(existMovement -> {
            existMovement.setMovementType(data.getMovementType());
            existMovement.setAmount(data.getAmount());
            existMovement.setBalance(data.getBalance());
            existMovement.setDate(new Date());
            return movementRepository.save(existMovement);
        }).map(updateMovement -> mapperConvert.toDTO(updateMovement, MovementDTO.class));
    }
}
