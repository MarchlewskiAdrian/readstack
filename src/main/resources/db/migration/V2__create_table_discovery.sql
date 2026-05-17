CREATE TABLE IF NOT EXISTS discovery
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(80)  NOT NULL UNIQUE,
    url         VARCHAR(500) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    category_id BIGINT          NOT NULL,
    uuid        UUID         NOT NULL UNIQUE,
    created_on  TIMESTAMP,
    version     BIGINT,
    FOREIGN KEY (category_id) REFERENCES category (id)
);