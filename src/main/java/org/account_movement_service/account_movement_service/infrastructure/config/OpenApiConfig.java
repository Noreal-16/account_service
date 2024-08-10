package org.account_movement_service.account_movement_service.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Evaluation API",
                version = "1.0",
                contact = @Contact(
                        name = "WebFlux Spring Boot"
                ),
                license = @License(
                        name = "OpenApi YML", url = "http://localhost:8082/v3/api-docs.yaml"
                ),
                description = "Chapter"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8082",
                        description = "Local"
                ),
                @Server(
                        url = "https://chapter-dev.webflux.com/api/v1/evaluation",
                        description = "Development"
                )}
)
public class OpenApiConfig {
}
