package org.account_movement_service.account_movement_service.application.interfaces.customerService;

import org.account_movement_service.account_movement_service.application.imp.customerImp.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDto> getInfoCustomerByIdentification(String identification);

    Mono<CustomerDto> getInfoCustomerById(Long id);
}
