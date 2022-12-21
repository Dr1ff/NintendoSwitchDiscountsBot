CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE discount (
                          id BIGINT PRIMARY KEY,
                          game_id BIGINT,
                          price_with_discount NUMERIC(19, 2)
);

CREATE TABLE game (
                      id BIGINT PRIMARY KEY,
                      "name" VARCHAR(255),
                      price NUMERIC(19, 2),
                      region VARCHAR(255)
);

CREATE TABLE notification (
                              id BIGSERIAL PRIMARY KEY,
                              created TIMESTAMP,
                              discount_id BIGINT,
                              next_push_date TIMESTAMP,
                              user_id BIGINT
);

CREATE TABLE "user" (
                        id BIGINT PRIMARY KEY ,
                        region VARCHAR(255),
                        wishlist BIGINT
);