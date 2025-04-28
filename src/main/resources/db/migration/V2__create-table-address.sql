CREATE TABLE address (
    id          SERIAL PRIMARY KEY,
    zipCode     VARCHAR(25)     NOT NULL,
    street      VARCHAR(255)    NOT NULL,
    number      INTEGER         NOT NULL,
    complement  VARCHAR(255)    NULL,
    state       VARCHAR(2)      NOT NULL,
    city        VARCHAR(50)     NOT NULL,
    userId      INTEGER REFERENCES users (id) NOT NULL
);
