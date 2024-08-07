package org.account_movement_service.account_movement_service.application.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovementDTO {
    Long id;
    String type;
    String movementType;
    Double balance;
    Double amount;
    Date date;
    String accountNumber;
}
