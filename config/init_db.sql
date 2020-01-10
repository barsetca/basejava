create table if not exists resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table if not exists contact
(
    id            serial   not null
        constraint contact_pk
            primary key,
    resume_uuid   char(36) not null
        constraint contact_resume_uuid_fk
            references resume
            on update restrict on delete cascade,
    contact_type  text     not null,
    contact_value text     not null

);

alter table contact
    owner to postgres;

create unique index if not exists contact_uuid_type_index
    on contact (resume_uuid, contact_type);

create table if not exists section
(
    id            serial   not null
        constraint section_pk
            primary key,
    section_type  text     not null,
    section_value text     not null,
    resume_uuid   char(36) not null
        constraint section_resume_uuid_fk
            references resume
            on update restrict on delete cascade
);

alter table section
    owner to postgres;

create unique index if not exists section_uuid_type_index
    on section (resume_uuid, section_type);

