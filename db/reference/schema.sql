
create sequence seq_email_logs_id start with 1 increment by 1;

create sequence seq_enumeration_item_id start with 1 increment by 1;

create sequence seq_event_id start with 1 increment by 1;

create sequence seq_event_term_id start with 1 increment by 1;

create sequence seq_lesson_id start with 1 increment by 1;

create sequence seq_payment_id start with 1 increment by 1;

create sequence seq_person_id start with 1 increment by 1;

create sequence seq_registration_id start with 1 increment by 1;

create sequence seq_user_id start with 1 increment by 1;

create table reg_app_param (
    name varchar(100) not null,
    value varchar(200) not null,
    primary key (name)
);

create table reg_attendance (
    created_at timestamp(6) not null,
    created_by varchar(50),
    modified_at timestamp(6),
    modified_by varchar(50),
    status varchar(10) not null,
    lesson_id bigint not null,
    person_id bigint not null,
    primary key (lesson_id, person_id)
);

create table reg_attendance_trainer (
    created_at timestamp(6) not null,
    created_by varchar(50),
    modified_at timestamp(6),
    modified_by varchar(50),
    duration_minutes integer not null,
    lesson_id bigint not null,
    person_id bigint not null,
    primary key (lesson_id, person_id)
);

create table reg_email_logs (
    id bigint not null,
    created_at timestamp(6) not null,
    email_type varchar(50) not null,
    html varchar(25000) not null,
    recipients varchar(50) not null,
    registration_id bigint not null,
    primary key (id)
);

create table reg_enumeration_item (
    id bigint not null,
    code varchar(10) not null,
    description varchar(500),
    enum_name varchar(40) not null,
    hidden boolean,
    latitude numeric(18,15),
    longitude numeric(18,15),
    name varchar(100) not null,
    ordering integer not null,
    primary key (id),
    unique (enum_name, code)
);

create table reg_event (
    id bigint not null,
    created_at timestamp(6) not null,
    created_by varchar(50),
    modified_at timestamp(6),
    modified_by varchar(50),
    uuid varchar(40) not null unique,
    details varchar(2000) not null,
    event_type varchar(20) not null,
    image_url varchar(100),
    locked timestamp(6),
    name varchar(200) not null,
    period_code varchar(10),
    place_code varchar(10) not null,
    registration_ends timestamp(6) not null,
    registration_starts timestamp(6) not null,
    status varchar(10) not null,
    primary key (id)
);

create table reg_event_condition (
    event_id bigint not null,
    condition_type varchar(20) not null,
    max_value varchar(50),
    min_value varchar(50),
    value varchar(50),
    ind integer not null,
    primary key (event_id, ind)
);

create table reg_event_term (
    id bigint not null,
    created_at timestamp(6) not null,
    created_by varchar(50),
    modified_at timestamp(6),
    modified_by varchar(50),
    capacity bigint not null,
    day_of_week varchar(10) check (day_of_week in ('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY')),
    deposit numeric(38,2),
    duration_minutes integer not null,
    end_date date,
    expected_trainers jsonb,
    has_attendance boolean not null,
    number_of_lessons bigint,
    price numeric(38,2) not null,
    repeat_type varchar(10) not null,
    start_date date not null,
    start_time time(6) not null,
    tags jsonb,
    event_id bigint not null,
    primary key (id)
);

create table reg_known_person (
    registration_id bigint not null,
    name varchar(50) not null,
    relation_code varchar(10) not null,
    surname varchar(50) not null,
    ind integer not null,
    primary key (registration_id, ind)
);

create table reg_lesson (
    id bigint not null,
    created_at timestamp(6) not null,
    created_by varchar(50),
    modified_at timestamp(6),
    modified_by varchar(50),
    duration_minutes integer not null,
    expected_trainers jsonb,
    place_code varchar(10) not null,
    start_at timestamp(6) not null,
    event_term_id bigint not null,
    primary key (id)
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
    id bigint not null,
    date_of_birth date not null,
    food_allergy_notes varchar(1000),
    health_notes varchar(1000),
    is_independent boolean not null,
    name varchar(50) not null,
    shirt_size varchar(10),
    surname varchar(50) not null,
    user_id bigint,
    primary key (id)
);

create table reg_registration (
    id bigint not null,
    created_at timestamp(6) not null,
    consent_gdpr boolean not null,
    consent_photo boolean,
    email varchar(100) not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    payment_scheme varchar(10),
    status varchar(10) not null,
    tel_phone varchar(20) not null,
    uuid varchar(40) not null,
    event_term_id bigint not null,
    person_id bigint not null,
    primary key (id)
);

create table reg_substitute_lesson (
    person_id bigint not null,
    expires_at timestamp(6),
    lesson_id bigint,
    created_at timestamp(6) not null,
    ind integer not null,
    primary key (person_id, ind)
);

create table reg_user (
    id bigint not null,
    created_at timestamp(6) not null,
    disabled boolean,
    email varchar(100) not null unique,
    name varchar(50) not null,
    roles jsonb,
    surname varchar(50) not null,
    telPhone varchar(20) not null,
    verified boolean,
    primary key (id)
);

create table reg_user_known_person (
    user_id bigint not null,
    name varchar(50) not null,
    relation_code varchar(10) not null,
    surname varchar(50) not null,
    ind integer not null,
    primary key (user_id, ind)
);

create index FKrude4o9qg6b1kehdii9sqxf2x on reg_attendance (lesson_id);
alter table if exists reg_attendance 
   add constraint FKrude4o9qg6b1kehdii9sqxf2x 
   foreign key (lesson_id) 
   references reg_lesson;

create index FKeh9qwfyea5pwv0y0spc8j6rw9 on reg_attendance (person_id);
alter table if exists reg_attendance 
   add constraint FKeh9qwfyea5pwv0y0spc8j6rw9 
   foreign key (person_id) 
   references reg_person;

create index FKdx49qbmdqvrfhbr4k70u4ohq9 on reg_attendance_trainer (lesson_id);
alter table if exists reg_attendance_trainer 
   add constraint FKdx49qbmdqvrfhbr4k70u4ohq9 
   foreign key (lesson_id) 
   references reg_lesson;

create index FKruj6st46w25y23h6x0173l4jr on reg_attendance_trainer (person_id);
alter table if exists reg_attendance_trainer 
   add constraint FKruj6st46w25y23h6x0173l4jr 
   foreign key (person_id) 
   references reg_person;

create index FKn5r2rv274w1jmhrfopttnlmgh on reg_event_condition (event_id);
alter table if exists reg_event_condition 
   add constraint FKn5r2rv274w1jmhrfopttnlmgh 
   foreign key (event_id) 
   references reg_event;

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

create index FK7badtl7mhctw5h9v7wkccuc1x on reg_lesson (event_term_id);
alter table if exists reg_lesson 
   add constraint FK7badtl7mhctw5h9v7wkccuc1x 
   foreign key (event_term_id) 
   references reg_event_term;

create index FKbjayrwgy5k5b0sgo32v5k9bvq on reg_person (user_id);
alter table if exists reg_person 
   add constraint FKbjayrwgy5k5b0sgo32v5k9bvq 
   foreign key (user_id) 
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

create index FK31csvudebfdubxfcph0os1jk3 on reg_substitute_lesson (lesson_id);
alter table if exists reg_substitute_lesson 
   add constraint FK31csvudebfdubxfcph0os1jk3 
   foreign key (lesson_id) 
   references reg_lesson;

create index FK15avd1i3eo3x9byi7og8jf8ki on reg_substitute_lesson (person_id);
alter table if exists reg_substitute_lesson 
   add constraint FK15avd1i3eo3x9byi7og8jf8ki 
   foreign key (person_id) 
   references reg_person;

create index FKdfbkmq04awfqi59v3ogpeewow on reg_user_known_person (user_id);
alter table if exists reg_user_known_person 
   add constraint FKdfbkmq04awfqi59v3ogpeewow 
   foreign key (user_id) 
   references reg_user;
