package org.account_movement_service.account_movement_service.infrastructure.router.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.account_movement_service.account_movement_service.application.dto.accountDto.AccountDTO;
import org.account_movement_service.account_movement_service.application.dto.accountDto.ResAccountDto;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@RouterOperations({
        @RouterOperation(
                method = RequestMethod.GET,
                path = "/api/v1/accounts",
                operation =
                @Operation(
                        description = "Obtener todos las cuentas",
                        operationId = "getAll",
                        tags = "Cuentas",
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Todos las cuentas.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        array = @ArraySchema(schema = @Schema(implementation = ResAccountDto.class)))
                                        })
                        })),

        @RouterOperation(
                method = RequestMethod.GET,
                path = "/api/v1/accounts/{id}",
                operation =
                @Operation(
                        description = "Obtener información del Cuentas por su ID.",
                        operationId = "getById",
                        tags = "Cuentas",
                        parameters = {
                                @Parameter(name = "id", in = ParameterIn.PATH)
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cuentas encontrado.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = ResAccountDto.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/api/v1/accounts",
                operation =
                @Operation(
                        description = "Registrar un Cuentas.",
                        operationId = "create",
                        tags = "Cuentas",
                        requestBody =
                        @RequestBody(
                                description = "Body para crear un Cuentas.",
                                required = true,
                                content = @Content(schema = @Schema(implementation = AccountDTO.class,
                                        requiredProperties = {" accountNumber", "accountType", "initialBalance", "identification"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cuentas registrado exitosamente.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = ResAccountDto.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.PUT,
                path = "/api/v1/accounts/{id}",
                operation =
                @Operation(
                        description = "Actualizar un Cuentas.",
                        operationId = "update",
                        tags = "Cuentas",
                        parameters = {
                                @Parameter(name = "id", in = ParameterIn.PATH)
                        },
                        requestBody =
                        @RequestBody(
                                description = ".",
                                required = true,
                                content = @Content(schema = @Schema(implementation = AccountDTO.class,
                                        requiredProperties = {"accountType", "status"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cuentas actualizado exitosamente.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = AccountDTO.class))
                                        })
                        })),


        @RouterOperation(
                method = RequestMethod.DELETE,
                path = "/api/v1/customers/{id}",
                operation =
                @Operation(
                        description = "Eliminar un Cuentas por su ID.",
                        operationId = "delete",
                        tags = "Cuentas",
                        parameters = {
                                @Parameter(name = "id", in = ParameterIn.PATH)
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cuentas eliminado con éxito.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE
                                                )})
                        }))
})
public @interface AccountsOpenApi {
}
