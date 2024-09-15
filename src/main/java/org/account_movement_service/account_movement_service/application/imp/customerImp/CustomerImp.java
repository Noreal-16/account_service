package org.account_movement_service.account_movement_service.application.imp.customerImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.application.imp.customerImp.dto.CustomerDto;
import org.account_movement_service.account_movement_service.application.interfaces.customerService.CustomerService;
import org.account_movement_service.account_movement_service.infrastructure.externalApi.HttpClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerImp implements CustomerService {

    private final HttpClientService httpClientService;
    @Value("${uri.base.http.url}")
    private String uri;

    @Override
    public Mono<CustomerDto> getInfoCustomerByIdentification(String identification) {
        String apiCustomer = "/api/v1/customers/" + identification;
        return httpClientService.get(uri + apiCustomer, CustomerDto.class);
    }

    @Override
    public Mono<CustomerDto> getInfoCustomerById(Long id) {
        String apiCustomer = "/api/v1/customers/info-customer/" + id;
        return httpClientService.get(uri + apiCustomer, CustomerDto.class);
    }
}
