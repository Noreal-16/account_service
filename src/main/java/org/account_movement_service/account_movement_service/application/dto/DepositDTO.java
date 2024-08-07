package org.account_movement_service.account_movement_service.application.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositDTO {
    String depositAccount;
    Double amount;
}
