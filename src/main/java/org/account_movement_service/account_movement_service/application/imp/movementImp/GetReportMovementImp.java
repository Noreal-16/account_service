package org.account_movement_service.account_movement_service.application.imp.movementImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.dto.movementDto.ResReportDto;
import org.account_movement_service.account_movement_service.application.interfaces.movementService.GetReportMovementService;
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
public class GetReportMovementImp implements GetReportMovementService {

    private final MovementRepository movementRepository;
    private final GetInfoCustomerGrpcImp getInfoCustomerGrpcImp;

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
