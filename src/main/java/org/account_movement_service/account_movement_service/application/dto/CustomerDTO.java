package org.account_movement_service.account_movement_service.application.dto;

import lombok.Data;

@Data
public class CustomerDTO {
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

