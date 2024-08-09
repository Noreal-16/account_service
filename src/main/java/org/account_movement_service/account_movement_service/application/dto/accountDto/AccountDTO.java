package org.account_movement_service.account_movement_service.application.dto.accountDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDTO {
    Long id;
    String accountNumber;
    String accountType;
    Double initialBalance;
    Boolean status;
    String identification;
}
