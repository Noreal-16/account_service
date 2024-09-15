package org.account_movement_service.account_movement_service.application.imp.movementImp.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementDTO {
    Long id;
    String type;
    String movementType;
    Double balance;
    Double amount;
    String accountNumber;
}
