--liquibase formatted sql
--changeset dugong:dev_001_enum

INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'XS', 'XS', 1);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'S', 'S', 2);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'M', 'M', 3);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'L', 'L', 4);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_SHIRT_SIZE', 'XL', 'XL', 5);


INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'MOM', 'Mama', 1);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'DAD', 'Otec', 2);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'SIS', 'Sestra', 3);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'BRO', 'Brat', 4);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'GRM', 'Babka', 5);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'GRD', 'Dedko', 6);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'UNC', 'Strýko', 7);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'AUN', 'Teta', 8);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_RELATION', 'OTH', 'Iný Vzťah', 9);

INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_EVENT_TYPE', 'EVENT', 'Udalosť', 1);
INSERT INTO reg_enumeration_item (id, enum_name, code, name, ordering) VALUES (nextval('seq_enumeration_item_id'), 'REG_EVENT_TYPE', 'ECA', 'Krúžok', 2);
