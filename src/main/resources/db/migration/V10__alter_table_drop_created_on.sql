ALTER TABLE category
    DROP COLUMN created_on;

ALTER TABLE discovery
    DROP COLUMN created_on;

ALTER TABLE users
    DROP COLUMN created_on;

ALTER TABLE votes
    DROP COLUMN created_on;