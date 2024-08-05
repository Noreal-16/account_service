package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.UpdateAccountService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateAccountImp implements UpdateAccountService {

    private final AccountRepository accountRepository;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperConvert;

    @Override
    public Mono<AccountDTO> update(Long id, AccountDTO data) {
        return accountRepository.findById(id)
                .flatMap(account -> {
                    account.setAccountType(data.getAccountType());
                    account.setStatus(data.getStatus());
                    return accountRepository.save(account);
                }).map(updateAccount -> mapperConvert.toDTO(updateAccount, AccountDTO.class));
    }
}
