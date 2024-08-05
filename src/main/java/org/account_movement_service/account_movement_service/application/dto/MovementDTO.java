package org.account_movement_service.account_movement_service.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MovementDTO {
    Long id;
    String movementType;
    Double balance;
    Double amount;
    Date date;
    Long accountId;

}
