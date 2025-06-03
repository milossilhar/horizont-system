--liquibase formatted sql
--changeset dugong:005_APP_PARAMS

create table reg_app_param (
    name varchar(100) not null,
    value varchar(200) not null,
    primary key (name)
);

insert into reg_app_param (name, value) values ('SCHEDULE_PAYMENT_INFO_ENABLE', '1');
insert into reg_app_param (name, value) values ('SCHEDULE_PAYMENT_INFO_BATCH_SIZE', '50');
