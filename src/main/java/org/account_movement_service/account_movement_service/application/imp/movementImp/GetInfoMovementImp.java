package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetInfoMovementService;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetInfoMovementImp implements GetInfoMovementService {

    private final MovementRepository movementRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;

    @Override
    public Mono<MovementDTO> getInfo(Long id) {
        return movementRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro movimiento con el id: " + id)))
                .map(getInfoMovement -> mapperConvert.toDTO(getInfoMovement, MovementDTO.class));
    }
}
