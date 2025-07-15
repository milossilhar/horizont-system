--liquibase formatted sql
--changeset dugong:INSERT_TABORY_25_DATA

INSERT INTO reg_event (id, created_at, uuid, event_type, name, details, place, reg_start_at, reg_end_at, image_url, discount_type) VALUES
    (nextval('seq_event_id'), current_timestamp, '15aec1f8-0679-4de7-8774-b9e7c80697f8', 'EVENT', 'Letný Camp 2025',
    'Po druhý krát organizujeme Camp na Slnečných skalách pri Žiline. Po veľkom úspechu sa uvidíme opäť! Zažijeme týždeň pod stanom a to od nedele do piatku.',
    'CAMP Slnečné skaly, Rajecké Teplice', '2025-04-26 08:00', '2025-08-10 23:59', '2025-camp.jpg', '25CAMP');

INSERT INTO reg_event (id, created_at, uuid, event_type, name, details, place, reg_start_at, reg_end_at, image_url, discount_type) VALUES
    (nextval('seq_event_id'), current_timestamp, '59cd6916-dfc3-42ed-87c6-09e9f8f7c0d2', 'CAMP', 'Denný Letný Tábor 2025',
    'Náš obľúbený koncept táborov je tu aj tento rok, už po tretí krát! Tento rok sme si pre decká nachystali veľa lezenia, zábavy, športových aktivít, jógy, plávania, batikovania a v neposlednom rade lezenia na skalách. Tábory prebiehajú na lezeckej stene v squash centre v Pezinku, posledný (piaty deň) lezieme na Medvedej skale pri Modre, kde treba deti doviezť na parkovisko pod skalami (v prípade nepriaznivého počasia lezieme aj v piatok na lezeckej stene v Pezinku).',
    'Lezecká stena v Squash Centre Pezinok, posledný deň Medvedia skala pri Modre', '2025-04-26 08:00', '2025-07-06 23:59', '2025-tabor.jpg', '25TABOR');

INSERT INTO reg_event (id, created_at, uuid, event_type, name, details, place, reg_start_at, reg_end_at, image_url, discount_type) VALUES
    (nextval('seq_event_id'), current_timestamp, 'a8084393-29af-4aaa-9f75-32b93b53e5a0', 'EVENT', 'Jednodňový Letný Tábor',
    'Po prvý krát Vám predstavujeme koncept jednodenných táborov. Tento rok sa uskutočnia v dvoch termínoch na Medvedej skale pri Modre. Tábor je určený pre deti, ktoré si chcú vyskúšať lezenie na skalách, je určený pre začiatočníkov aj pre pokročilých lezcov. Tento koncept je vhodný pre deti od 6 do 16 rokov. Deti je potrebné priviezť 8:00 na parkovisko pod skalami nad Modrou a vyzdvihnúť na rovnakom parkovisku 15:30.',
    'Medvedia skala pri Modre', '2025-04-26 08:00', '2025-07-06 23:59', '2025-daily.jpg', null);

-- Denný Letný Tábor 2025
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    VALUES (nextval('seq_event_term_id'), '2025-07-14 07:30:00', '2025-07-18 16:00:00', 25, 50, 210, (SELECT id FROM reg_event WHERE uuid = '59cd6916-dfc3-42ed-87c6-09e9f8f7c0d2'));
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    VALUES (nextval('seq_event_term_id'), '2025-07-21 07:30:00', '2025-07-25 16:00:00', 25, 50, 210, (SELECT id FROM reg_event WHERE uuid = '59cd6916-dfc3-42ed-87c6-09e9f8f7c0d2'));
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    VALUES (nextval('seq_event_term_id'), '2025-07-28 07:30:00', '2025-08-01 16:00:00', 25, 50, 210, (SELECT id FROM reg_event WHERE uuid = '59cd6916-dfc3-42ed-87c6-09e9f8f7c0d2'));
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
     VALUES (nextval('seq_event_term_id'), '2025-08-04 07:30:00', '2025-08-08 16:00:00', 25, 50, 210, (SELECT id FROM reg_event WHERE uuid = '59cd6916-dfc3-42ed-87c6-09e9f8f7c0d2'));

-- Jednodňový Letný Tábor
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    VALUES (nextval('seq_event_term_id'), '2025-07-08 08:00:00', '2025-07-08 16:00:00', 25, 0, 50, (SELECT id FROM reg_event WHERE uuid = 'a8084393-29af-4aaa-9f75-32b93b53e5a0'));
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    VALUES (nextval('seq_event_term_id'), '2025-07-10 08:00:00', '2025-07-10 16:00:00', 25, 0, 50, (SELECT id FROM reg_event WHERE uuid = 'a8084393-29af-4aaa-9f75-32b93b53e5a0'));

-- Letný Camp 2025
INSERT INTO reg_event_term (id, start_at, end_at, capacity, deposit, price, event_id)
    VALUES (nextval('seq_event_term_id'), '2025-08-17 12:30:00', '2025-08-22 12:00:00', 20, 100, 340, (SELECT id FROM reg_event WHERE uuid = '15aec1f8-0679-4de7-8774-b9e7c80697f8'));
