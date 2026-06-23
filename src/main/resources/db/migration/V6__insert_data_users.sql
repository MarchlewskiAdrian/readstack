INSERT INTO users (username, email, password, enabled,
                   authorities, uuid, created_on, version)
VALUES ('admin',
        'admin@example.com',
        '$2a$10$d1A0WszTXHAnbTznNooEQ.K9Eps/uKH/5lTlJ28E1aXvej1X3V.Bu',
        TRUE,
        ARRAY ['ROLE_ADMIN'],
        '32ea50fd-abcb-4ede-a7ac-b28c5ab56f00',
        NULL,
        0),

       ('string',
        'string@example.com',
        '$2a$10$9VAZvYh/IO5sxPgx9XWwheD5EcwPgY1W0HLXQTkxOr5HnCKrTfxGm',
        TRUE,
        ARRAY ['ROLE_USER'],
        '629fe4d8-7f38-4e39-9b2f-e13c208ab5be',
        TIMESTAMP '2026-06-20 18:11:55.758387',
        1)
ON CONFLICT (id) DO NOTHING;