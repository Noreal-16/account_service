package org.account_movement_service.account_movement_service.infrastructure.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.SneakyThrows;
import org.client_person_service.client_person_service.grpc.CustomerRequire;
import org.client_person_service.client_person_service.grpc.CustomerRequireByAccount;
import org.client_person_service.client_person_service.grpc.CustomerResponse;
import org.client_person_service.client_person_service.grpc.CustomerServiceGrpc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
                    return customerServiceStub.getInfoCustomerById(request);
                }).subscribeOn(Schedulers.boundedElastic()).onErrorResume(e -> Mono.empty())
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty());
    }

    public Mono<CustomerResponse> getInfoCustomerByIdentification(String identification) {
        return Mono.fromCallable(() -> {
                    CustomerRequireByAccount request = CustomerRequireByAccount.newBuilder()
                            .setIdentification(identification)
                            .build();
                    return customerServiceStub.getInfoCustomerByAccount(request);
                }).subscribeOn(Schedulers.boundedElastic()).onErrorResume(e -> Mono.empty())
                .filter(Objects::nonNull)
                .switchIfEmpty(Mono.empty());

    }

    @SneakyThrows
    public void shutdown() {
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
