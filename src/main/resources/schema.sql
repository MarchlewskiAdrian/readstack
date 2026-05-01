CREATE TABLE IF NOT EXISTS category
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    uuid        UUID        NOT NULL,
    created_on  TIMESTAMP,
    version     BIGINT
);

CREATE TABLE IF NOT EXISTS discovery
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(80)  NOT NULL UNIQUE,
    url         VARCHAR(500) NOT NULL UNIQUE,
    description VARCHAR(500) NOT NULL,
    category_id BIGINT          NOT NULL,
    uuid        UUID         NOT NULL,
    created_on  TIMESTAMP,
    version     BIGINT,
    FOREIGN KEY (category_id) REFERENCES category (id)
);