--liquibase formatted sql
--changeset dugong:004_EMAIL_FLAGS_REGISTRATION

ALTER TABLE reg_registration ADD email_confirm_sent boolean;
ALTER TABLE reg_registration ADD email_payment_confirm_sent boolean;
ALTER TABLE reg_registration ADD email_payment_info_sent boolean;
