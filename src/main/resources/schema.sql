create table if not exists accounts (
    id                  SERIAL primary key,
    account_number      varchar(50) not null ,
    account_type        varchar(10) not null,
    initial_balance     DECIMAL not null ,
    status              boolean default true,
    customer_id         int not null
    );

create table if not exists movements (
    id                  SERIAL primary key,
    movement_type       varchar(50) not null ,
    balance             DECIMAL  NOT NULL,
    amount              DECIMAL  not null,
    date                DATE,
    account_id          int not null
    );