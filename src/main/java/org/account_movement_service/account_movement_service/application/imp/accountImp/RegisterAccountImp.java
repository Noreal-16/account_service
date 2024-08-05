package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.RegisterAccountService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterAccountImp implements RegisterAccountService {

    private final AccountRepository accountRepository;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperConvert;

    @Override
    public Mono<AccountDTO> register(AccountDTO data) {
        AccountsEntity accountsEntity = mapperConvert.toENTITY(data, AccountsEntity.class);
        return accountRepository.save(accountsEntity)
                .map(accountSave -> mapperConvert.toDTO(accountSave, AccountDTO.class));
    }
}
