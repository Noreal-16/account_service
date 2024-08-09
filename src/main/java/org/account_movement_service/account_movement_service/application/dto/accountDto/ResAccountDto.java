package org.account_movement_service.account_movement_service.application.dto.accountDto;

import lombok.Data;

@Data
public class ResAccountDto {
    Long id;
    String accountNumber;
    String accountType;
    Double initialBalance;
    Boolean status;
    String names;
}
