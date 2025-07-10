--liquibase formatted sql
--changeset dugong:test_data

-- reg_enumeration_item
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering)
    VALUES
        -- REG_E_SHIRT_SIZE
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'XS', 'XS', 1),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'S', 'S', 2),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'M', 'M', 3),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'L', 'L', 4),
        (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'XL', 'XL', 5),
        -- REG_E_RELATION
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'MOM', 'Mama', 1),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'DAD', 'Otec', 2),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'SIS', 'Sestra', 3),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'BRO', 'Brat', 4),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'GRM', 'Babka', 5),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'GRD', 'Dedko', 6),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'UNC', 'Strýko', 7),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'AUN', 'Teta', 8),
        (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'OTH', 'Iný Vzťah', 9)
;

-- reg_event
INSERT INTO reg_event (id, name, created_at, uuid, details, event_type, status, registration_starts, registration_ends, place_code) VALUES
    (1, 'Tabory', current_timestamp, '000001', 'Nejaký super duper popis', 'ECA', 'DRAFT', current_timestamp - interval '1d', current_timestamp + interval '1y', 'BLCK_DCK')
;

-- updating sequences
SELECT setval('seq_event_id', 10);
