package org.account_movement_service.account_movement_service.application.interfaces.movementService;

import org.account_movement_service.account_movement_service.application.dto.movementDto.ResReportDto;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface GetReportMovementService {

    Flux<ResReportDto> getReport(Long accountId, LocalDate startDate, LocalDate endDate);

}
