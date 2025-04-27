CREATE TABLE users (
    id          SERIAL PRIMARY KEY,
    email       VARCHAR(150)    NOT NULL UNIQUE,
    password    TEXT            NOT NULL,
    firstName   VARCHAR(150)    NOT NULL,
    lastName    VARCHAR(150)    NOT NULL,
    phoneNumber VARCHAR(20)     NULL
);
