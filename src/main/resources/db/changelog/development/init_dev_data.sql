--liquibase formatted sql
--changeset dugong:DEV_INIT

-- REG_EVENT
insert into reg_event (id, created_at, uuid, event_type, name, details, place, reg_end_at, reg_start_at)
    values (1, current_timestamp, '774444ca-7e2a-49dc-80ee-9d98a500bfb4', 'CAMP', 'Letné Tábory 2025', 'Týždeň zábavy, oddychu a lezenia.', 'Squash Centrum PK', '2025-04-29 12:00', '2025-04-29 14:00');
insert into reg_event (id, created_at, uuid, event_type, name, details, place, reg_end_at, reg_start_at)
    values (2, current_timestamp, '9b3481e6-a61b-46be-9c3f-134d3921a02b', 'EVENT', 'Letný denný Tábor 2025', 'Deň táboru na skalách', 'Medvedia skala, Modra', '2025-04-29 12:00', '2025-04-29 14:00');
insert into reg_event (id, created_at, uuid, event_type, name, details, place, reg_end_at, reg_start_at)
    values (3, current_timestamp, 'f6e36f20-fcd6-445e-88ce-4eff5175ca73', 'EVENT', 'Letný týždňový Camp', 'Týždeň bez rodičov na Slenčných skalách', 'Slnečné skaly, Žilina', '2025-04-29 12:00', '2025-04-29 14:00');

-- REG_EVENT_TERM
-- tyzdenny denny tabor
insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    values (1, '2025-07-14 07:30:00', '2025-07-18 16:00:00', 20, 50, 200, 1);
insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    values (2, '2025-07-21 07:30:00', '2025-07-25 16:00:00', 20, 50, 200, 1);
insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    values (3, '2025-07-28 07:30:00', '2025-08-01 16:00:00', 20, 50, 200, 1);
 insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
     values (4, '2025-08-04 07:30:00', '2025-08-08 16:00:00', 20, 50, 200, 1);

-- denny tabor
insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    values (5, '2025-07-08 08:00:00', '2025-07-08 16:00:00', 20, 15, 60, 2);
insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    values (6, '2025-07-10 08:00:00', '2025-07-10 16:00:00', 20, 15, 60, 2);

-- tyzdenny camp
insert into reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    values (7, '2025-07-14 07:30:00', '2025-07-18 16:00:00', 25, 100, 400, 3);

-- update sequences
select setval('seq_event_id', 100, false);
select setval('seq_event_term_id', 100, false);

