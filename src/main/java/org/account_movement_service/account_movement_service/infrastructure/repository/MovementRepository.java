package org.account_movement_service.account_movement_service.infrastructure.repository;

import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovementRepository extends R2dbcRepository<MovementsEntity, Long> {

    Mono<MovementsEntity> findFirstByAccountIdOrderByDateDesc(Long accountId);

}
