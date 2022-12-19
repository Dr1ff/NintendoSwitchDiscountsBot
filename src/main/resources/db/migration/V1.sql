create sequence hibernate_sequence start 1 increment 1;

create table discount (
    id bigint not null,
    game_id bigint,
    price_with_discount numeric(19, 2),
    primary key (id)
);

create table game (
    id bigint primary key,
    "name" varchar(255),
    price numeric(19, 2),
    region varchar(255)
);

create table notification (
    id bigserial primary key,
    created timestamp,
    discount_id bigint,
    next_push_date timestamp,
    user_id bigint
);

create table "user" (
    id bigint primary key,
    region varchar(255),
    wishlist bigint
);