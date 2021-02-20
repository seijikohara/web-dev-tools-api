create table html
(
    id bigserial not null
        constraint html_pk
            primary key,
    name varchar not null,
    code integer not null,
    code2 integer,
    standard varchar,
    dtd varchar,
    description varchar
);

create unique index html_name_uindex
    on html (name);

