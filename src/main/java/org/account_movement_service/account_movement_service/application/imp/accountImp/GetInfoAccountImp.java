package org.account_movement_service.account_movement_service.application.imp.accountImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.GetInfoAccountService;
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
@RequiredArgsConstructor
public class GetInfoAccountImp implements GetInfoAccountService {

    private final AccountRepository accountRepository;
    private final MapperConvert<ResAccountDto, AccountsEntity> mapperConvert;
    private final GetInfoCustomerGrpcImp getInfoCustomerGrpcImp;

    @Override
    public Mono<ResAccountDto> getInfo(Long id) {
        return accountRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontro cuenta con el id: " + id)))
                .flatMap(account -> getInfoCustomerGrpcImp.getInfoCustomer(account.getCustomerId().toString())
                            .map(customerDto -> {
                                ResAccountDto resAccountDto = mapperConvert.toDTO(account, ResAccountDto.class);
                                resAccountDto.setNames(customerDto.getName());
                                return resAccountDto;
                            })
                ).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró información del cliente.")));
    }
}
