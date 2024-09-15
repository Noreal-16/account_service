package org.account_movement_service.account_movement_service.application.imp.movementImp.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResReportDto {
    LocalDate date;
    String names;
    String accountNumber;
    String type;
    Number initial_balance;
    Boolean status;
    Number amount;
    Number balance;
}
