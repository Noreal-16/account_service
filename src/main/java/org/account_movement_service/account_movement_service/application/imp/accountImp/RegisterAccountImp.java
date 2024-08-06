package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.RegisterAccountService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.grpc.GetInfoCustomerGrpcImp;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class RegisterAccountImp implements RegisterAccountService {

    private final AccountRepository accountRepository;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperConvert;
    private GetInfoCustomerGrpcImp getInfoCustomerGrpcImp;

    @Override
    public Mono<AccountDTO> register(AccountDTO data) {
        return getInfoCustomerGrpcImp.getInfoCustomer(data.getCustomerId().toString())
                .flatMap(customerEntity -> {
                    if (customerEntity != null) {
                        AccountsEntity accountsEntity = mapperConvert.toENTITY(data, AccountsEntity.class);
                        accountsEntity.setStatus(true);
                        accountsEntity.setCustomerId(Long.parseLong(customerEntity.getId()));
                        return accountRepository.save(accountsEntity)
                                .map(accountSave -> mapperConvert.toDTO(accountSave, AccountDTO.class));
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
                    }
                });
    }

}
