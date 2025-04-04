
create sequence seq_enumeration_item_id start with 1 increment by 1;

create sequence seq_event_id start with 1 increment by 1;

create sequence seq_event_term_id start with 1 increment by 1;

create sequence seq_payment_id start with 1 increment by 1;

create sequence seq_person_id start with 1 increment by 1;

create sequence seq_registration_id start with 1 increment by 1;

create sequence seq_user_id start with 1 increment by 1;

create table reg_enumeration_item (
    id bigint not null,
    enum_code varchar(30),
    enum_name varchar(40) check (enum_name in ('REG_E_EVENT_CONDITION_TYPE','REG_E_EVENT_DISCOUNT_TYPE','REG_E_RELATION','REG_E_SHIRT_SIZE')),
    enum_value varchar(150),
    visible boolean,
    primary key (id)
);

create table reg_event (
    id bigint not null,
    created_at timestamp(6) not null,
    uuid varchar(40) not null unique,
    event_type varchar(20) not null check (event_type in ('EVENT','CAMP','SCHOOL_CLIMB','ECA')),
    event_name varchar(200) not null,
    reg_end_at timestamp(6) not null,
    reg_start_at timestamp(6) not null,
    primary key (id)
);

create table reg_event_condition (
    event_id bigint not null,
    condition_type varchar(10),
    condition_value varchar(50),
    max_value numeric(38,2),
    min_value numeric(38,2),
    ind integer not null,
    primary key (event_id, ind)
);

create table reg_event_discount (
    event_id bigint not null,
    condition_value integer,
    discount_type varchar(10),
    percent numeric(4,1),
    value integer,
    ind integer not null,
    primary key (event_id, ind)
);

create table reg_event_term (
    id bigint not null,
    end_at timestamp(6) not null,
    start_at timestamp(6) not null,
    event_id bigint not null,
    primary key (id)
);

create table reg_payment (
    id bigint not null,
    deposit numeric(10,2),
    deposit_paid boolean,
    discount_percent numeric(4,1),
    discount_value numeric(8,2),
    paid boolean,
    price numeric(10,2) not null,
    registration_id bigint not null,
    user_id bigint,
    primary key (id)
);

create table reg_person (
    id bigint not null,
    date_of_birth date,
    food_allergy_notes varchar(1000),
    health_notes varchar(1000),
    is_independent boolean not null,
    person_name varchar(200) not null,
    shirt_size varchar(10),
    person_surname varchar(200) not null,
    parent_id bigint,
    primary key (id)
);

create table reg_registration (
    id bigint not null,
    created_at timestamp(6) not null,
    confirmed_by varchar(255),
    transaction_id varchar(40) not null,
    event_term_id bigint,
    person_id bigint,
    primary key (id)
);

create table reg_user (
    id bigint not null,
    created_at timestamp(6) not null,
    email varchar(100) unique,
    name varchar(50),
    surname varchar(50),
    tel_phone varchar(20),
    primary key (id)
);

create table reg_user_known_person (
    user_id bigint not null,
    name varchar(50),
    relation varchar(5),
    surname varchar(50),
    ind integer not null,
    primary key (user_id, ind)
);

create index FKn5r2rv274w1jmhrfopttnlmgh on reg_event_condition (event_id);
alter table if exists reg_event_condition
   add constraint FKn5r2rv274w1jmhrfopttnlmgh
   foreign key (event_id)
   references reg_event;

create index FK37nq1yojxb68umlgpaa05e814 on reg_event_discount (event_id);
alter table if exists reg_event_discount
   add constraint FK37nq1yojxb68umlgpaa05e814
   foreign key (event_id)
   references reg_event;

create index FK3amist06sen6r7d9m164epbr4 on reg_event_term (event_id);
alter table if exists reg_event_term
   add constraint FK3amist06sen6r7d9m164epbr4
   foreign key (event_id)
   references reg_event;

create index FKt2m1t119i0ch62h2nl4c9fsq0 on reg_payment (user_id);
alter table if exists reg_payment
   add constraint FKt2m1t119i0ch62h2nl4c9fsq0
   foreign key (user_id)
   references reg_user;

create index FKh4s4wkl0eprwmr4tc9y0ch485 on reg_person (parent_id);
alter table if exists reg_person
   add constraint FKh4s4wkl0eprwmr4tc9y0ch485
   foreign key (parent_id)
   references reg_user;

create index FKli5l6y8g2i9ww46ctuewlhw0h on reg_registration (event_term_id);
alter table if exists reg_registration
   add constraint FKli5l6y8g2i9ww46ctuewlhw0h
   foreign key (event_term_id)
   references reg_event_term;

create index FKnjnpvb8l9ocogq7bkibd61yt9 on reg_registration (person_id);
alter table if exists reg_registration
   add constraint FKnjnpvb8l9ocogq7bkibd61yt9
   foreign key (person_id)
   references reg_person;

create index FKdfbkmq04awfqi59v3ogpeewow on reg_user_known_person (user_id);
alter table if exists reg_user_known_person
   add constraint FKdfbkmq04awfqi59v3ogpeewow
   foreign key (user_id)
   references reg_user;
