package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.RegisterMovementService;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.account_movement_service.account_movement_service.infrastructure.utils.enums.MovementsEnums;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterMovementImp implements RegisterMovementService {

    private final MovementRepository movementRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;

    @Override
    public Mono<MovementDTO> registerMovement(Long accountId, Double amount, Double newBalance) {
        MovementsEntity movement = new MovementsEntity();
        movement.setMovementType(amount < 0 ? MovementsEnums.DEBIT + "of" + amount : MovementsEnums.CREDIT + "of" + amount);
        movement.setBalance(newBalance);
        movement.setAmount(amount);
        movement.setAccountId(accountId);

        return movementRepository.save(movement)
                .map(savedMovement -> mapperConvert.toDTO(savedMovement, MovementDTO.class));

    }
}
