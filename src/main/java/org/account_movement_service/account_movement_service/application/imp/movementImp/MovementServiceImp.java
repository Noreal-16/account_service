package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.movementDto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.ResReportDto;
import org.account_movement_service.account_movement_service.application.dto.movementDto.TransferDTO;
import org.account_movement_service.account_movement_service.application.imp.accountImp.GetInfoAccountByAccountNumberImp;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetMovementByAccountIdService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.MovementService;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.RegisterMovementService;
import org.account_movement_service.account_movement_service.infrastructure.exceptions.CustomException;
import org.account_movement_service.account_movement_service.infrastructure.grpc.GetInfoCustomerGrpcImp;
import org.account_movement_service.account_movement_service.infrastructure.repository.MovementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class MovementServiceImp implements MovementService {
    private final RegisterMovementService registerMovementService;
    private final GetInfoAccountByAccountNumberImp getAccountNumber;
    private final GetMovementByAccountIdService getMovementByAccountId;
    private final RegisterMovementService registerMovement;
    private final MovementRepository movementRepository;
    private final GetInfoCustomerGrpcImp getInfoCustomerGrpcImp;

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

    @Override
    public Flux<ResReportDto> getReport(Long accountId, LocalDate startDate, LocalDate endDate) {
        return getInfoCustomerGrpcImp.getInfoCustomer(accountId.toString())
                .switchIfEmpty(Mono.error(new CustomException("No se encontraron movimientos para el cliente especificado.", HttpStatus.NOT_FOUND)))
                .flatMapMany(customer -> movementRepository.findInfoReport(accountId, startDate, endDate)
                        .map(report -> {
                            log.info(report.toString());
                            report.setNames(customer.getName());
                            return report;
                        })).switchIfEmpty(Flux.empty());
    }
}
