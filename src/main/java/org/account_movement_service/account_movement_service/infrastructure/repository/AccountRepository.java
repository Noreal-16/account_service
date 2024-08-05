package org.account_movement_service.account_movement_service.infrastructure.repository;

import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends R2dbcRepository<AccountsEntity, Long> {
}
