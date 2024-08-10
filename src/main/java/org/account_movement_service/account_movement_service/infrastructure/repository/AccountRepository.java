package org.account_movement_service.account_movement_service.infrastructure.repository;

import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AccountRepository extends R2dbcRepository<AccountsEntity, Long> {

    @Query("SELECT ac.* FROM accounts ac where ac.account_number = :accountNumber")
    Mono<AccountsEntity> findByAccountNumber(@Param("accountNumber") String accountNumber);

}
