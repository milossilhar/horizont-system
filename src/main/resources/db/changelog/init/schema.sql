
create sequence REG_PERSON_ID start with 1 increment by 1;

create table REG_PERSON (
    id bigint not null,
    DATE_OF_BIRTH date,
    NAME varchar(255),
    SURNAME varchar(255),
    primary key (id)
);
