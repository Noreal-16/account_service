package org.account_movement_service.account_movement_service.infrastructure.router.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.account_movement_service.account_movement_service.application.dto.movementDto.DepositDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.MovementDTO;
import org.account_movement_service.account_movement_service.application.dto.movementDto.ResReportDto;
import org.account_movement_service.account_movement_service.application.dto.movementDto.TransferDTO;
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
                method = RequestMethod.POST,
                path = "/api/v1/movements/deposit",
                operation =
                @Operation(
                        description = "Registrar un deposito.",
                        operationId = "create",
                        tags = "Movimientos",
                        requestBody =
                        @RequestBody(
                                description = "Body para crear un movimiento.",
                                required = true,
                                content = @Content(schema = @Schema(implementation = DepositDTO.class,
                                        requiredProperties = {" depositAccount", "amount"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cliente registrado exitosamente.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = MovementDTO.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.POST,
                path = "/api/v1/movements/transfer",
                operation =
                @Operation(
                        description = "Registrar una transferencia.",
                        operationId = "create",
                        tags = "Movimientos",
                        requestBody =
                        @RequestBody(
                                description = "Body para crear un movimiento.",
                                required = true,
                                content = @Content(schema = @Schema(implementation = TransferDTO.class,
                                        requiredProperties = {" originAccount", "destinationAccount", "amount"}))),
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Cliente registrado exitosamente.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        schema = @Schema(implementation = MovementDTO.class))
                                        })
                        })),
        @RouterOperation(
                method = RequestMethod.GET,
                path = "/reports/{customer_id}",
                operation =
                @Operation(
                        description = "Obtener Reporte de movimientos.",
                        operationId = "getAll",
                        tags = "Movimientos",
                        parameters = {
                                @Parameter(name = "customer_id", in = ParameterIn.PATH,description = "Id del cliente"),
                                @Parameter(name = "startDate", in = ParameterIn.QUERY, description = "Fecha de inicio", required = true, example = "2024-08-07"),
                                @Parameter(name = "endDate", in = ParameterIn.QUERY, description = "Fecha de fin", required = true, example = "2024-08-09")
                        },
                        responses = {
                                @ApiResponse(
                                        responseCode = "200",
                                        description = "Todos los Movimientos.",
                                        content = {
                                                @Content(
                                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                        array = @ArraySchema(schema = @Schema(implementation = ResReportDto.class)))
                                        })
                        })),


})
public @interface MovementsOpenApi {
}
