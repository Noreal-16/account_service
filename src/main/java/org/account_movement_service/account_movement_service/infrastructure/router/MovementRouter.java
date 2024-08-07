package org.account_movement_service.account_movement_service.infrastructure.router;

import lombok.extern.slf4j.Slf4j;
import org.account_movement_service.account_movement_service.infrastructure.handler.MovementsHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@Slf4j
public class MovementRouter {

    private static final String PATH = "/api/v1/movements";

    @Bean
    RouterFunction<ServerResponse> routes(MovementsHandler movementsHandler) {
        return RouterFunctions.route()
                .GET(PATH, movementsHandler::getAllHandler)
                .GET(PATH + "/{id}", movementsHandler::getInfoHandler)
                .POST(PATH, movementsHandler::registerHandler)
                .PUT(PATH + "/{id}", movementsHandler::updateHandler)
                .DELETE(PATH + "/{id}", movementsHandler::deleteHandler)
                .build();
    }

}
