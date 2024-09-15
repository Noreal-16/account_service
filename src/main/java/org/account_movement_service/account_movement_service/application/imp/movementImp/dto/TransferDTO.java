package org.account_movement_service.account_movement_service.application.imp.movementImp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferDTO {
    @NotNull(message = "Account number cannot be null")
    @NotBlank(message = "Account number cannot be empty")
    String originAccount;
    @NotNull(message = "Account number cannot be null")
    @NotBlank(message = "Account number cannot be empty")
    String destinationAccount;
    @NotNull(message = "Amount cannot be null")
    Double amount;
}
