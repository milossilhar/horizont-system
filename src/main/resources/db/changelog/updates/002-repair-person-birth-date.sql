--liquibase formatted sql
--changeset dugong:002_REPAIR_PERSON_BIRTH_DATE

UPDATE reg_person SET date_of_birth = date_of_birth + INTERVAL '1d';
