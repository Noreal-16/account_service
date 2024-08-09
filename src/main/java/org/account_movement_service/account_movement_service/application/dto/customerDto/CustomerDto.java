package org.account_movement_service.account_movement_service.application.dto.customerDto;

import lombok.Data;

@Data
public class CustomerDto {
    Long id;
    String name;
    String gender;
    Integer age;
    String identification;
    String direction;
    String phone;
    String password;
    Boolean status;
    Long personId;
}
