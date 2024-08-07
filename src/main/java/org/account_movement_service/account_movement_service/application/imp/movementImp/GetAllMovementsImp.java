package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetAllMovementService;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class GetAllMovementsImp implements GetAllMovementService {

    private final MovementRepository movementRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;

    @Override
    public Flux<MovementDTO> getAll() {
        return movementRepository.findAll().map(listMovements -> mapperConvert.toDTO(listMovements, MovementDTO.class));
    }
}
