CREATE TABLE IF NOT EXISTS category
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    uuid        UUID        NOT NULL UNIQUE,
    created_on  TIMESTAMP,
    version     BIGINT
);