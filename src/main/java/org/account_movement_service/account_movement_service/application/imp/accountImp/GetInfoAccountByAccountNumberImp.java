package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.imp.accountImp.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.GetInfoAccountByAccountNumberService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.exceptions.CustomException;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetInfoAccountByAccountNumberImp implements GetInfoAccountByAccountNumberService {

    private final AccountRepository accountRepository;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperConvert;

    @Override
    public Mono<AccountDTO> getInfoAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new CustomException("Cuenta No encontrada : " + accountNumber, HttpStatus.NOT_FOUND)))
                .map(accountsEntity -> mapperConvert.toDTO(accountsEntity, AccountDTO.class));
    }
}
