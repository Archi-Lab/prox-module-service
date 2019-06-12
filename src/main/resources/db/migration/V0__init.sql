create table hops_module_mapping
(
    id        uuid not null,
    kuerzel   varchar(255),
    version   varchar(255),
    module_id uuid,
    primary key (id)
);

create table hops_study_course_mapping
(
    id                  uuid not null,
    study_course_kürzel varchar(255),
    study_course_id     uuid,
    primary key (id)
);

create table module
(
    id          uuid not null,
    description varchar(9000),
    name        varchar(255),
    module_id   uuid,
    primary key (id)
);

create table study_course
(
    id                     uuid not null,
    academic_degree        int4,
    name                   varchar(255),
    parent_study_course_id uuid,
    primary key (id)
);

alter table module
    add constraint FK_module_study_course
        foreign key (module_id) references study_course;

alter table study_course
    add constraint FK_study_course_study_course
        foreign key (parent_study_course_id) references study_course;
