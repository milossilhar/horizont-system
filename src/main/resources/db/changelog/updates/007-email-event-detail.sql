--liquibase formatted sql
--changeset dugong:007_EMAIL_EVENT_DETAIL

-- remove registration constraint
alter table reg_registration drop constraint if exists reg_registration_status_check;

-- add columns
alter table reg_registration add email_detail_sent boolean;
alter table reg_registration add email_payment_complete_confirm_sent boolean;

-- new app params
insert into reg_app_param (name, value) values ('SCHEDULE_EVENT_DETAIL_ENABLE', '0');
insert into reg_app_param (name, value) values ('SCHEDULE_EVENT_DETAIL_BATCH_SIZE', '50');
