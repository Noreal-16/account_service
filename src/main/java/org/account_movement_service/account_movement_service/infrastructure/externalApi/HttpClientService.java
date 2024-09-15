package org.account_movement_service.account_movement_service.infrastructure.externalApi;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HttpClientService {
    private final WebClient webClient;

    public HttpClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public <T> Mono<T> get(String url, Class<T> responseType) {
        return this.webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(throwable -> Mono.empty());
    }

}
