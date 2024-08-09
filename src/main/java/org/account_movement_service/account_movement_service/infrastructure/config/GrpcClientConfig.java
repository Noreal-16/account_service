package org.account_movement_service.account_movement_service.infrastructure.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {

    @Value("${api.base.grpc.url}")
    private String grpcUri;
    @Value("${api.base.grpc.port}")
    private int grpcPort;


    @Bean(destroyMethod = "shutdown")
    public ManagedChannel managedChannel() {
        return ManagedChannelBuilder.forAddress(grpcUri, grpcPort)
                .usePlaintext()
                .build();
    }
}
