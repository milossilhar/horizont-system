--liquibase formatted sql
--changeset dugong:prod_001_enum

INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'XS', 'XS', 1, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'S', 'S', 2, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'M', 'M', 3, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'L', 'L', 4, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_SHIRT_SIZE', 'XL', 'XL', 5, true);


INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'MOM', 'Mama', 1, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'DAD', 'Otec', 2, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'SIS', 'Sestra', 3, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'BRO', 'Brat', 4, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'GRM', 'Babka', 5, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'GRD', 'Dedko', 6, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'UNC', 'Strýko', 7, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'AUN', 'Teta', 8, true);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_RELATION', 'OTH', 'Iný Vzťah', 9, true);

INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_EVENT_DISCOUNT_TYPE', '25TABOR', 'Letný Tábor 2025', 1, false);
INSERT INTO reg_enumeration_item (id, enum_name, code, description, ordering, visible) VALUES (nextval('seq_enumeration_item_id'), 'REG_E_EVENT_DISCOUNT_TYPE', '25CAMP', 'Letný Camp 2025', 2, false);
