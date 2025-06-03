--liquibase formatted sql
--changeset dugong:006_EMAIL_LOGS

create sequence seq_email_logs_id start with 1 increment by 1;

create table reg_email_logs (
    id bigint not null,
    created_at timestamp(6) not null,
    email_type varchar(50) not null,
    html varchar(25000) not null,
    recipients varchar(50) not null,
    registration_id bigint not null,
    primary key (id)
);
