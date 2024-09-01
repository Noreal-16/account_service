package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.AccountService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.exceptions.CustomException;
import org.account_movement_service.account_movement_service.infrastructure.grpc.GetInfoCustomerGrpcImp;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImp implements AccountService {
    private final AccountRepository accountRepository;
    private final MapperConvert<ResAccountDto, AccountsEntity> mapperConvert;
    private final MapperConvert<AccountDTO, AccountsEntity> mapperAccountConvert;
    private final GetInfoCustomerGrpcImp getInfoCustomerGrpcImp;

    @Override
    public Flux<ResAccountDto> getAll() {
        return accountRepository.findAll()
                .flatMap(accounts -> getInfoCustomerGrpcImp.getInfoCustomer(accounts.getCustomerId().toString())
                        .map(listAccount -> {
                            ResAccountDto resAccountDto = mapperConvert.toDTO(accounts, ResAccountDto.class);
                            resAccountDto.setNames(listAccount.getName());
                            return resAccountDto;
                        }))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<ResAccountDto> getInfo(Long id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException("No se encontro cuenta con el id: " + id, HttpStatus.NOT_FOUND)))
                .flatMap(account -> getInfoCustomerGrpcImp.getInfoCustomer(account.getCustomerId().toString())
                        .map(customerDto -> {
                            ResAccountDto resAccountDto = mapperConvert.toDTO(account, ResAccountDto.class);
                            resAccountDto.setNames(customerDto.getName());
                            return resAccountDto;
                        })
                ).switchIfEmpty(Mono.error(new CustomException("No se encontró información del cliente.", HttpStatus.NOT_FOUND)));
    }


    @Override
    public Mono<ResAccountDto> register(AccountDTO data) {
        return getInfoCustomerGrpcImp.getInfoCustomerByIdentification(data.getIdentification())
                .switchIfEmpty(Mono.error(new CustomException("Cliente no se encuentra registrado", HttpStatus.NOT_FOUND)))
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
                        return Mono.error(new CustomException("Cliente no se encuentra registrado", HttpStatus.NOT_FOUND));
                    }
                });
    }


    @Override
    public Mono<AccountDTO> update(Long id, AccountDTO data) {
        return accountRepository.findById(id)
                .flatMap(account -> {
                    account.setAccountType(data.getAccountType());
                    account.setStatus(data.getStatus());
                    return accountRepository.save(account);
                }).map(updateAccount -> mapperAccountConvert.toDTO(updateAccount, AccountDTO.class));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return accountRepository.findById(id)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(account -> accountRepository.deleteById(account.getId()))
                .then();
    }
}
