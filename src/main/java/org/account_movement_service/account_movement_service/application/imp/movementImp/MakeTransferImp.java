package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.TransferDTO;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeTransferService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeTransferImp implements MakeTransferService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;
    private final MapperConvert<AccountDTO, AccountsEntity> accountsEntityMapper;


    @Override
    public Mono<MovementDTO> makeTransfer(TransferDTO transferDTO) {
        return getAccount(transferDTO.getOriginAccount())
                .doOnSuccess(account -> log.info("Cuenta encontrada --->: {}", account))
                .flatMap(originAccount -> {
                    if (validateAccount(originAccount.getAccountNumber(), transferDTO.getDestinationAccount())) {
                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede realizar transferencia entre la misma cuenta."));
                    }
                    return getMovementByAccountId(originAccount.getId())
                            .defaultIfEmpty(new MovementDTO())
                            .doOnSuccess(movement -> log.info("Moviemiento encontrado 1 --->: {}", movement))
                            .flatMap(lastMovement -> {
                                log.info("lastMovement --->: {}", lastMovement);
                                Double availableBalance = lastMovement.getId() == null ? originAccount.getInitialBalance() : lastMovement.getBalance();
                                if (availableBalance < transferDTO.getAmount()) {
                                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente. Saldo actual: " + availableBalance));
                                }
                                return getAccount(transferDTO.getDestinationAccount()).flatMap(destinationAccount -> {
                                    if (validateAccount(originAccount.getAccountNumber(), transferDTO.getDestinationAccount())) {
                                        return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede realizar transferencia entre la misma cuenta."));
                                    }
                                    Double newOriginBalance = availableBalance - transferDTO.getAmount();
                                    return getMovementByAccountId(destinationAccount.getId())
                                            .defaultIfEmpty(new MovementDTO())
                                            .flatMap(lastDesMovement -> {
                                                Double destinationAvailableBalance = lastMovement.getId() == null ? destinationAccount.getInitialBalance() : lastMovement.getBalance();
                                                Double newDestinationBalance = destinationAvailableBalance + transferDTO.getAmount();
                                                return createMovement(originAccount.getId(), -transferDTO.getAmount(), newOriginBalance)
                                                        .then(createMovement(destinationAccount.getId(), transferDTO.getAmount(), newDestinationBalance));
                                            });
                                });
                            });
                });
    }

    private Mono<MovementDTO> createMovement(Long accountId, Double amount, Double newBalance) {
        MovementsEntity movement = new MovementsEntity();
        movement.setMovementType(amount < 0 ? "DEBIT" : "CREDIT");
        movement.setBalance(newBalance);
        movement.setAmount(amount);
        movement.setAccountId(accountId);

        return movementRepository.save(movement)
                .map(savedMovement -> mapperConvert.toDTO(savedMovement, MovementDTO.class));
    }

    private Boolean validateAccount(String originAccount, String destinationAccount) {
        return destinationAccount.equals(originAccount);
    }

    private Mono<AccountDTO> getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta No encontrada : " + accountNumber)))
                .map(accountsEntity -> accountsEntityMapper.toDTO(accountsEntity, AccountDTO.class));
    }

    private Mono<MovementDTO> getMovementByAccountId(Long movementId) {
        return movementRepository.findFirstByAccountIdOrderByDateDesc(movementId)
                .doOnSuccess(movement -> log.info("Moviemiento encontrado 1 --->: {}", movement))
                .map(existMovement -> mapperConvert.toDTO(existMovement, MovementDTO.class))
                .switchIfEmpty(Mono.empty())
                .doOnError(error -> log.error("Error al obtener movimiento", error));
    }

}
