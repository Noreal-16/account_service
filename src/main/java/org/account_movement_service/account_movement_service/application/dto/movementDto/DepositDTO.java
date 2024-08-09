package org.account_movement_service.account_movement_service.application.dto.movementDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositDTO {

    @NotNull(message = "Deposit account cannot be null")
    @NotBlank(message = "Deposit account cannot be empty")
    String depositAccount;
    @NotNull(message = "Amount cannot be null")
    Double amount;
}
