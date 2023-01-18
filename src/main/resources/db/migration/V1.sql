CREATE SEQUENCE hibernate_sequence START 1 INCREMENT 1;

CREATE TABLE discount (
                          id BIGINT PRIMARY KEY,
                          game_id BIGINT,
                          price_with_discount NUMERIC(19, 2)
);

CREATE TABLE game (
                      "name" TEXT NOT NULL,
                      country TEXT NOT NULL,
                      actual_price NUMERIC NOT NULL,
                      price_without_discount NUMERIC,
                      discount_percent FLOAT8,
                      price_valid_until TIMESTAMP,
                      is_discount BOOLEAN NOT NULL
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
                        country TEXT NOT NULL,
                        wishlist JSONB
);