package org.account_movement_service.account_movement_service.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
import org.account_movement_service.account_movement_service.application.interfaces.accountService.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<ResAccountDto> getAll() {
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResAccountDto> getAccountById(@PathVariable Long id) {
        return accountService.getInfo(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResAccountDto> registerAccount(@RequestBody @Validated AccountDTO data) {
        return accountService.register(data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AccountDTO> updateAccount(@RequestBody @Validated AccountDTO data, @PathVariable Long id) {
        return accountService.update(id, data);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateAccount(@PathVariable Long id) {
        return accountService.delete(id);
    }
}
