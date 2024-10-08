package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.imp.movementImp.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetMovementByAccountIdService;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetMovementByAccountIdImp implements GetMovementByAccountIdService {

    private final MovementRepository movementRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;


    @Override
    public Mono<MovementDTO> getMovementByAccountId(Long movementId) {
        return movementRepository.findFirstByAccountIdOrderByDateDesc(movementId)
                .map(existMovement -> mapperConvert.toDTO(existMovement, MovementDTO.class))
                .switchIfEmpty(Mono.empty());
    }
}
