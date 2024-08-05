package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.GetAllAccountsService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllAccountsImp implements GetAllAccountsService {

    private final AccountRepository accountRepository;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperConvert;

    @Override
    public Flux<AccountDTO> getAll() {
        return accountRepository.findAll()
                .map(listAccount -> mapperConvert.toDTO(listAccount, AccountDTO.class))
                .switchIfEmpty(Flux.empty());
    }
}
