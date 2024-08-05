package org.account_movement_service.account_movement_service.application.dto;

import lombok.Data;

@Data
public class AccountDTO {
    Long id;
    String accountNumber;
    String accountType;
    Double initialBalance;
    Boolean status;
    Long customerId;
}
