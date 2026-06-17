CREATE TABLE IF NOT EXISTS votes
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT      NOT NULL,
    discovery_id BIGINT      NOT NULL,
    type         VARCHAR(10) NOT NULL,
    version      BIGINT,
    created_on   TIMESTAMP,
    uuid         UUID        NOT NULL UNIQUE,
    created      TIMESTAMP WITH TIME ZONE,
    creator      BIGINT,
    updated      TIMESTAMP WITH TIME ZONE,
    updater      BIGINT,

    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (discovery_id) REFERENCES discovery (id),
    CONSTRAINT uk_vote_discovery_user UNIQUE (user_id, discovery_id),
    CONSTRAINT chk_vote_type CHECK ( type in ('UP', 'DOWN'))
);