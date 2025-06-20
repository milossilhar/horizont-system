--liquibase formatted sql
--changeset dugong:test_data

-- reg_enumeration_item
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible)
    VALUES
        -- REG_E_SHIRT_SIZE
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'XS', 'XS', 1, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'S', 'S', 2, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'M', 'M', 3, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'L', 'L', 4, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'XL', 'XL', 5, true),
        -- REG_E_RELATION
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'MOM', 'Mama', 1, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'DAD', 'Otec', 2, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'SIS', 'Sestra', 3, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'BRO', 'Brat', 4, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'GRM', 'Babka', 5, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'GRD', 'Dedko', 6, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'UNC', 'Strýko', 7, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'AUN', 'Teta', 8, true),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'OTH', 'Iný Vzťah', 9, true),
        -- REG_E_EVENT_DISCOUNT_TYPE
        (nextval('seq_enumeration_item_id'), 'REG_E_EVENT_DISCOUNT_TYPE', '25TABOR', 'Letný Tábor 2025', 1, false),
        (nextval('seq_enumeration_item_id'), 'REG_E_EVENT_DISCOUNT_TYPE', '25CAMP', 'Letný Camp 2025', 2, false)
;

-- reg_period
INSERT INTO reg_period (id, created_at, created_by, name)
    VALUES
        (1, current_timestamp, 'liquibase', 'Jeseň 2024'),
        (2, current_timestamp, 'liquibase', 'Jar 2025'),
        (3, current_timestamp, 'liquibase', 'Leto 2025')
;

-- reg_place
INSERT INTO reg_place (id, name, created_at, latitude, longitude) VALUES
    (1, 'SQUASH Pezinok', current_timestamp, 15.6565, 17.8856)
;

-- reg_event
INSERT INTO reg_event (id, name, created_at, uuid, details, event_type, reg_start_at, reg_end_at, period_id, place_id) VALUES
    (1, 'Tabory', current_timestamp, '000001', 'Nejaký super duper popis', 'ECA', current_timestamp - interval '1d', current_timestamp + interval '1y', 2, 1)
;

-- updating sequences
SELECT setval('seq_period_id', 10);
SELECT setval('seq_place_id', 10);
SELECT setval('seq_event_id', 10);




