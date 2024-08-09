package org.account_movement_service.account_movement_service.application.interfaces.accountService;

import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import reactor.core.publisher.Mono;

public interface GetInfoAccountByAccountNumberService {

    Mono<AccountDTO> getInfoAccountByAccountNumber(String accountNumber);

}
