--liquibase formatted sql
--changeset dugong:TEST_EVENTS

insert into reg_event (id, created_at, uuid, event_type, event_name, reg_end_at, reg_start_at)
    values (nextval('seq_event_id'), current_timestamp, '4213215123412', 'CAMP', 'Letne Tabory 2025', '2025-04-29 12:00', '2025-04-29 14:00');
