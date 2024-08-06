package org.account_movement_service.account_movement_service.infrastructure.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.client_person_service.client_person_service.grpc.CustomerRequire;
import org.client_person_service.client_person_service.grpc.CustomerResponse;
import org.client_person_service.client_person_service.grpc.CustomerServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;

@Service
public class GetInfoCustomerGrpcImp {

    @Value("${api.base.grpc.url}")
    private String grpcUri;
    @Value("${api.base.grpc.port}")
    private int grpcPort;

    private CustomerServiceGrpc.CustomerServiceBlockingStub customerServiceStub;
    private ManagedChannel channel;

    @PostConstruct
    private void init() {
        channel = ManagedChannelBuilder.forAddress(grpcUri, grpcPort)
                .usePlaintext()
                .build();
        customerServiceStub = CustomerServiceGrpc.newBlockingStub(channel);
    }

    public Mono<CustomerResponse> getInfoCustomer(String customerId) {
        return Mono.fromCallable(() -> {
            CustomerRequire request = CustomerRequire.newBuilder()
                    .setId(customerId)
                    .build();
            return customerServiceStub.getInfoCustomer(request);
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
