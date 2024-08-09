package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
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
    private final MapperConvert<ResAccountDto, AccountsEntity> mapperConvert;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperAccountConvert;
    private final GetInfoCustomerGrpcImp getInfoCustomerGrpcImp;

    @Override
    public Mono<ResAccountDto> register(AccountDTO data) {
        return getInfoCustomerGrpcImp.getInfoCustomerByIdentification(data.getIdentification())
                .flatMap(customerEntity -> {
                    if (customerEntity != null) {
                        AccountsEntity accountsEntity = mapperAccountConvert.toENTITY(data, AccountsEntity.class);
                        accountsEntity.setStatus(true);
                        accountsEntity.setCustomerId(Long.parseLong(customerEntity.getId()));
                        return accountRepository.save(accountsEntity)
                                .map(accountSave -> {
                                    ResAccountDto responseDto = mapperConvert.toDTO(accountSave, ResAccountDto.class);
                                    responseDto.setNames(customerEntity.getName());
                                    return responseDto;
                                });
                    } else {
                        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no se encuentra registrado"));
                    }
                });
    }

}
