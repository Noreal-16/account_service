package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.DeleteAccountService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteAccountImp implements DeleteAccountService {

    private final AccountRepository accountRepository;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperConvert;

    @Override
    public Mono<Void> delete(Long id) {
        return accountRepository.findById(id)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(account -> accountRepository.deleteById(account.getId()))
                .then();
    }
}
