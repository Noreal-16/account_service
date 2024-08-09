package org.account_movement_service.account_movement_service.application.dto.accountDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    Long id;
    @NotNull(message = "Account number cannot be null")
    @NotBlank(message = "Account number cannot be empty")
    String accountNumber;
    @NotNull(message = "Account type cannot be null")
    @NotBlank(message = "Account type cannot be empty")
    String accountType;
    @NotNull(message = "Initial balance cannot be null")
    Double initialBalance;
    Boolean status;
    @NotNull(message = "Identification cannot be null")
    @NotBlank(message = "Identification cannot be empty")
    String identification;
}
