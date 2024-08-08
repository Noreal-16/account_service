package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeDepositService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.account_movement_service.account_movement_service.infrastructure.utils.enums.MovementsEnums;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeDepositImp implements MakeDepositService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;
    private final MapperConvert<AccountDTO, AccountsEntity> accountsEntityMapper;

    @Override
    public Mono<MovementDTO> makeDeposit(DepositDTO depositDTO) {
        MovementsEntity movementsEntity = new MovementsEntity();
        return getAccount(depositDTO.getDepositAccount())
                .flatMap(existAccount -> {
                    log.info("existAccount: {}", existAccount);
                    movementsEntity.setAccountId(existAccount.getId());
                    movementsEntity.setAmount(depositDTO.getAmount());
                    movementsEntity.setBalance(existAccount.getInitialBalance() + depositDTO.getAmount());
                    movementsEntity.setMovementType(MovementsEnums.CREDIT.toString());
                    return movementRepository
                            .save(movementsEntity)
                            .map(saveMovement -> mapperConvert.toDTO(saveMovement, MovementDTO.class))
                            .doOnSuccess(d -> log.info("Movimiento registrado satisfactoriamente"));
                });
    }


    private Mono<AccountDTO> getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta No encontrada : " + accountNumber)))
                .map(accountsEntity -> accountsEntityMapper.toDTO(accountsEntity, AccountDTO.class));
    }

}
