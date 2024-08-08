package org.account_movement_service.account_movement_service.infrastructure.repository;

import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MovementRepository extends R2dbcRepository<MovementsEntity, Long> {

    @Query("SELECT id, movement_type, balance, amount, account_id FROM public.movements m where m.account_id = :accountId order by date desc LIMIT 1")
    Mono<MovementsEntity> findFirstByAccountIdOrderByDateDesc(Long accountId);

}
