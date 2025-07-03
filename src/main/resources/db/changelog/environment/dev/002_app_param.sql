--liquibase formatted sql
--changeset dugong:dev_002_app_param

insert into reg_app_param (name, value)
    values
        ('SCHEDULE_PAYMENT_INFO_ENABLE', 'false');
