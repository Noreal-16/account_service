package org.account_movement_service.account_movement_service.infrastructure.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.*;
import org.account_movement_service.account_movement_service.infrastructure.exceptions.ErrorResponseCustomer;
import org.account_movement_service.account_movement_service.infrastructure.handler.interfaces.BaseHandler;
import org.account_movement_service.account_movement_service.infrastructure.utils.ObjectValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class AccountHandler implements BaseHandler {

    private final RegisterAccountService registerAccountService;
    private final GetAllAccountsService getAllAccountsService;
    private final GetInfoAccountService getInfoAccountService;
    private final UpdateAccountService updateAccountService;
    private final DeleteAccountService deleteAccountService;
    private final ObjectValidation objectValidation;


    @Override
    public Mono<ServerResponse> getAllHandler(ServerRequest serverRequest) {
        Flux<ResAccountDto> accountDTOFlux = getAllAccountsService.getAll();
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(accountDTOFlux, ResAccountDto.class)
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers")));
    }

    @Override
    public Mono<ServerResponse> getInfoHandler(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<ResAccountDto> accountDTOMono = getInfoAccountService.getInfo(id);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(accountDTOMono, ResAccountDto.class)
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers")));
    }

    @Override
    public Mono<ServerResponse> registerHandler(ServerRequest serverRequest) {
        Mono<AccountDTO> accountDTOMono = serverRequest.bodyToMono(AccountDTO.class).doOnNext(objectValidation::validate);
        return accountDTOMono.flatMap(accountEntity -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(registerAccountService.register(accountEntity), AccountDTO.class));
    }

    @Override
    public Mono<ServerResponse> updateHandler(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        Mono<AccountDTO> accountDTOMono = serverRequest.bodyToMono(AccountDTO.class).doOnNext(objectValidation::validate);
        return accountDTOMono.flatMap(accountEntity -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateAccountService.update(id, accountEntity), AccountDTO.class));
    }

    @Override
    public Mono<ServerResponse> deleteHandler(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(deleteAccountService.delete(id), AccountDTO.class)
                .onErrorResume(error -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .bodyValue(new ErrorResponseCustomer("INTERNAL_SERVER_ERROR", "Error occurred while fetching customers")));
    }
}
