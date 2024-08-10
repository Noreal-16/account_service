package org.account_movement_service.account_movement_service.infrastructure.router;

import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.infrastructure.handler.AccountHandler;
import org.account_movement_service.account_movement_service.infrastructure.router.docs.AccountsOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class AccountRouter {
    private static final String PATH = "/api/v1/accounts";

    @Bean
    @AccountsOpenApi
    RouterFunction<ServerResponse> accountRoutes(AccountHandler accountHandler) {
        return RouterFunctions.route()
                .GET(PATH, accountHandler::getAllHandler)
                .GET(PATH + "/{id}", accountHandler::getInfoHandler)
                .POST(PATH, accountHandler::registerHandler)
                .PUT(PATH + "/{id}", accountHandler::updateHandler)
                .DELETE(PATH + "/{id}", accountHandler::deleteHandler)
                .build();
    }
}
