package org.account_movement_service.account_movement_service.domain.accounts;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "ACCOUNTS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountsEntity {
    @Id
    Long id;
    @Column(value = "ACCOUNT_NUMBER")
    String accountNumber;
    @Column(value = "ACCOUNT_TYPE")
    String accountType;
    @Column(value = "INITIAL_BALANCE")
    Double initialBalance;
    Boolean status;
    @Column(value = "CUSTOMER_ID")
    Long customerId;
}
