-- liquibase formatted sql
-- changeset anna:1
create table socks
(
    id          bigserial primary key,
    color       varchar(100) not null,
    cotton_part smallint check (cotton_part > -1 and cotton_part < 101),
    quantity    bigint check (quantity > 0)
);

