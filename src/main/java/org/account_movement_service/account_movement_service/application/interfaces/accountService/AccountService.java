package org.account_movement_service.account_movement_service.application.interfaces.accountService;

import org.account_movement_service.account_movement_service.application.imp.accountImp.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.imp.accountImp.dto.ResAccountDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {
    Flux<ResAccountDto> getAll();

    Mono<ResAccountDto> getInfo(Long id);

    Mono<ResAccountDto> register(AccountDTO data);

    Mono<AccountDTO> update(Long id, AccountDTO data);

    Mono<Void> delete(Long id);
}
