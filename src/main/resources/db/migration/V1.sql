create sequence hibernate_sequence start 1 increment 1;

create table discount (
    id bigint not null,
    game_id bigint,
    price_with_discount numeric(19, 2),
    primary key (id)
);

create table game (
    id bigint not null,
    "name" varchar(255),
    price numeric(19, 2),
    region varchar(255),
    primary key (id)
);

create table notification (
    id bigint not null,
    created timestamp,
    discount_id bigint,
    next_push_date timestamp,
    user_id bigint,
    primary key (id)
);

create table "user" (
    id int8 not null,
    chat_id varchar(255),
    region varchar(255),
    wishlist bigint,
    primary key (id)
);