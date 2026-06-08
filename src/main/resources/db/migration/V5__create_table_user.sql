CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL UNIQUE,
    email       VARCHAR(100) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    enabled     BOOLEAN      NOT NULL,
    authorities TEXT[],
    uuid        UUID         NOT NULL UNIQUE,
    created_on  TIMESTAMP,
    version     BIGINT
);