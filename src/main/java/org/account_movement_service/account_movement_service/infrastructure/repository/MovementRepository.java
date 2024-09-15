package org.account_movement_service.account_movement_service.infrastructure.repository;

import org.account_movement_service.account_movement_service.application.imp.movementImp.dto.ResReportDto;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public interface MovementRepository extends R2dbcRepository<MovementsEntity, Long> {

    @Query("SELECT id, movement_type, balance, amount, account_id FROM public.movements m where m.account_id = :accountId order by date desc LIMIT 1")
    Mono<MovementsEntity> findFirstByAccountIdOrderByDateDesc(@Param("accountId") Long accountId);

    @Query("SELECT m.date, a.customer_id, a.account_number, m.movement_type as type, a.initial_balance , a.status, m.amount  , m.balance  " +
            "FROM movements m inner join accounts a on m.account_id = a.id  " +
            "where a.customer_id = :accountId and date BETWEEN :startDate and :endDate")
    Flux<ResReportDto> findInfoReport(@Param("accountId") Long accountId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

}
