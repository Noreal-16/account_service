package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.GetAllAccountsService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.infrastructure.grpc.GetInfoCustomerGrpcImp;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetAllAccountsImp implements GetAllAccountsService {

    private final AccountRepository accountRepository;
    private final MapperConvert<ResAccountDto, AccountsEntity> mapperConvert;
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
}
