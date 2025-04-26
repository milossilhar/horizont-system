
create sequence seq_enumeration_item_id start with 1 increment by 1;

create sequence seq_event_id start with 1 increment by 1;

create sequence seq_event_term_id start with 1 increment by 1;

create sequence seq_payment_id start with 1 increment by 1;

create sequence seq_registration_id start with 1 increment by 1;

create table reg_enumeration_item (
    id bigint not null,
    code varchar(10) not null,
    enum_name varchar(40) not null check (enum_name in ('REG_E_EVENT_CONDITION_TYPE','REG_E_EVENT_DISCOUNT_TYPE','REG_E_RELATION','REG_E_SHIRT_SIZE')),
    ordering integer not null,
    description varchar(150) not null,
    visible boolean not null,
    primary key (id)
);

create table reg_event (
    id bigint not null,
    created_at timestamp(6) not null,
    uuid varchar(40) not null unique,
    details varchar(2000) not null,
    discount_type varchar(40),
    event_type varchar(20) not null check (event_type in ('EVENT','CAMP','SCHOOL_CLIMB','ECA')),
    image_url varchar(100),
    name varchar(200) not null,
    place varchar(300) not null,
    reg_end_at timestamp(6) not null,
    reg_start_at timestamp(6) not null,
    primary key (id)
);

create table reg_event_term (
    id bigint not null,
    capacity integer not null,
    deposit numeric(38,2) not null,
    end_at timestamp(6) not null,
    price numeric(38,2) not null,
    start_at timestamp(6) not null,
    event_id bigint not null,
    primary key (id)
);

create table reg_known_person (
    registration_id bigint not null,
    name varchar(50) not null,
    relation varchar(10) not null,
    surname varchar(50) not null,
    ind integer not null,
    primary key (registration_id, ind)
);

create table reg_payment (
    id bigint not null,
    deposit numeric(10,2) not null,
    deposit_paid boolean,
    discount_percent numeric(4,1),
    discount_value numeric(8,2),
    paid boolean,
    price numeric(10,2) not null,
    primary key (id)
);

create table reg_person (
    registration_id bigint not null,
    date_of_birth date not null,
    food_allergy_notes varchar(1000),
    health_notes varchar(1000),
    is_independent boolean not null,
    name varchar(50) not null,
    shirt_size varchar(10),
    surname varchar(50) not null,
    ind integer not null,
    primary key (registration_id, ind)
);

create table reg_registration (
    id bigint not null,
    created_at timestamp(6) not null,
    uuid varchar(40) not null unique,
    consent_gdpr boolean not null,
    consent_photo boolean,
    email varchar(100) not null,
    name varchar(50) not null,
    status varchar(10) not null check (status in ('CONCEPT','QUEUE','ACCEPTED','CONFIRMED')),
    surname varchar(50) not null,
    tel_phone varchar(20) not null,
    event_term_id bigint,
    payment_id bigint unique,
    primary key (id)
);

create index FK3amist06sen6r7d9m164epbr4 on reg_event_term (event_id);
alter table if exists reg_event_term 
   add constraint FK3amist06sen6r7d9m164epbr4 
   foreign key (event_id) 
   references reg_event;

create index FKh8lbf0k7e2q2m17qn3xs5vgoo on reg_known_person (registration_id);
alter table if exists reg_known_person 
   add constraint FKh8lbf0k7e2q2m17qn3xs5vgoo 
   foreign key (registration_id) 
   references reg_registration;

create index FKmfsff7uq7d9ftjk360nuspw18 on reg_person (registration_id);
alter table if exists reg_person 
   add constraint FKmfsff7uq7d9ftjk360nuspw18 
   foreign key (registration_id) 
   references reg_registration;

create index FKli5l6y8g2i9ww46ctuewlhw0h on reg_registration (event_term_id);
alter table if exists reg_registration 
   add constraint FKli5l6y8g2i9ww46ctuewlhw0h 
   foreign key (event_term_id) 
   references reg_event_term;

create index FKlm2mjdcnm4qqn1dwm715yvwmp on reg_registration (payment_id);
alter table if exists reg_registration 
   add constraint FKlm2mjdcnm4qqn1dwm715yvwmp 
   foreign key (payment_id) 
   references reg_payment;
