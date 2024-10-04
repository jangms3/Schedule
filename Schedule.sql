create table schedules
(
    id         bigint auto_increment
        primary key,
    task       varchar(255)                        not null,
    creator    varchar(100)                        not null,
    password   varchar(255)                        not null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    updated_at timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

