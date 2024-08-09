package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.movementDto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.imp.accountImp.GetInfoAccountByAccountNumberImp;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetMovementByAccountIdService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MakeDepositService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.RegisterMovementService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class MakeDepositImp implements MakeDepositService {

    private final RegisterMovementService registerMovementService;
    private final GetInfoAccountByAccountNumberImp getAccountNumber;
    private final GetMovementByAccountIdService getMovementByAccountId;


    @Transactional
    @Override
    public Mono<MovementDTO> makeDeposit(DepositDTO depositDTO) {
        return getAccountNumber.getInfoAccountByAccountNumber(depositDTO.getDepositAccount())
                .flatMap(existAccount -> getMovementByAccountId.getMovementByAccountId(existAccount.getId())
                        .defaultIfEmpty(new MovementDTO())
                        .flatMap(movementExist -> {
                            Double firstMovementAmount = movementExist.getId() == null ? existAccount.getInitialBalance() : movementExist.getBalance();
                            Double newBalance = firstMovementAmount + depositDTO.getAmount();
                            return registerMovementService.registerMovement(existAccount.getId(), depositDTO.getAmount(), newBalance)
                                    .doOnSuccess(movement -> log.info("Se registro correctamente la transacción"));
                        }));
    }

}
