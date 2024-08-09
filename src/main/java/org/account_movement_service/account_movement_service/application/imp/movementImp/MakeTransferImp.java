package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.TransferDTO;
import org.account_movement_service.account_movement_service.application.imp.accountImp.GetInfoAccountByAccountNumberImp;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetMovementByAccountIdService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeTransferService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.RegisterMovementService;
import org.account_movement_service.account_movement_service.domain.accounts.AccountsEntity;
import org.account_movement_service.account_movement_service.domain.movements.MovementsEntity;
import org.account_movement_service.account_movement_service.infrastructure.exceptions.CustomException;
import org.account_movement_service.account_movement_service.infrastructure.repository.AccountRepository;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.account_movement_service.account_movement_service.infrastructure.utils.MapperConvert;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeTransferImp implements MakeTransferService {

    private final MovementRepository movementRepository;
    private final AccountRepository accountRepository;
    private final MapperConvert<MovementDTO, MovementsEntity> mapperConvert;
    private final MapperConvert<AccountDTO, AccountsEntity> accountsEntityMapper;
    private final RegisterMovementService registerMovement;
    private final GetInfoAccountByAccountNumberImp getAccountNumber;
    private final GetMovementByAccountIdService getMovementByAccountId;


    @Override
    public Mono<MovementDTO> makeTransfer(TransferDTO transferDTO) {
        return getAccountNumber.getInfoAccountByAccountNumber(transferDTO.getOriginAccount())
                .doOnSuccess(account -> log.info("Cuenta encontrada --->: {}", account))
                .flatMap(originAccount -> {
                    if (validateAccount(originAccount.getAccountNumber(), transferDTO.getDestinationAccount())) {
                        return Mono.error(new CustomException("No se puede realizar transferencia entre la misma cuenta.", HttpStatus.BAD_REQUEST));
                    }
                    return getMovementByAccountId.getMovementByAccountId(originAccount.getId())
                            .defaultIfEmpty(new MovementDTO())
                            .flatMap(lastMovement -> {
                                log.info("lastMovement --->: {}", lastMovement);
                                Double availableBalance = lastMovement.getId() == null ? originAccount.getInitialBalance() : lastMovement.getBalance();
                                if (availableBalance < transferDTO.getAmount()) {
                                    return Mono.error(new CustomException("Saldo insuficiente. Saldo actual: " + availableBalance, HttpStatus.BAD_REQUEST));
                                }
                                return getAccountNumber.getInfoAccountByAccountNumber(transferDTO.getDestinationAccount()).flatMap(destinationAccount -> {
                                    if (validateAccount(originAccount.getAccountNumber(), transferDTO.getDestinationAccount())) {
                                        return Mono.error(new CustomException("No se puede realizar transferencia entre la misma cuenta.", HttpStatus.BAD_REQUEST));
                                    }
                                    Double newOriginBalance = availableBalance - transferDTO.getAmount();
                                    return getMovementByAccountId.getMovementByAccountId(destinationAccount.getId())
                                            .defaultIfEmpty(new MovementDTO())
                                            .flatMap(lastDesMovement -> {
                                                Double destinationAvailableBalance = lastMovement.getId() == null ? destinationAccount.getInitialBalance() : lastMovement.getBalance();
                                                Double newDestinationBalance = destinationAvailableBalance + transferDTO.getAmount();
                                                return registerMovement.registerMovement(originAccount.getId(), -transferDTO.getAmount(), newOriginBalance)
                                                        .then(registerMovement.registerMovement(destinationAccount.getId(), transferDTO.getAmount(), newDestinationBalance));
                                            });
                                });
                            });
                });
    }


    private Boolean validateAccount(String originAccount, String destinationAccount) {
        return destinationAccount.equals(originAccount);
    }

}
