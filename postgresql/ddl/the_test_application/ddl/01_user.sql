\c krdb

CREATE SCHEMA app AUTHORIZATION "admin_kr";

CREATE TABLE app.users (
    uuid UUID PRIMARY KEY,
    dt_create TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    dt_update TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    email VARCHAR(255),
    fio VARCHAR(255),
    role VARCHAR(255),
    status VARCHAR(255),
    password VARCHAR(255)
);

ALTER TABLE IF EXISTS app.users
    OWNER to "admin_kr";

CREATE TABLE IF NOT EXISTS app.verification (
    email VARCHAR(255) PRIMARY KEY,
    code VARCHAR(255),
    status VARCHAR(255)
);

ALTER TABLE app.verification
    OWNER TO "admin_kr";

INSERT INTO app.users (
    uuid,
    dt_create,
    dt_update,
    email,
    fio,
    role,
    status,
    password
)
VALUES (
    '00000000-0000-0000-0000-000000000000',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'admin@gmail.com',
    'Admin Adminovich',
    'ROLE_ADMIN',
    'ACTIVATED',
    '$2a$10$kVx33idOsssroHLhLR7Bgu1WkJQ.N3Cy0Ma3u6Lcy.8GPuxwnbxmq'
);

INSERT INTO app.users (
    uuid,
    dt_create,
    dt_update,
    email,
    fio,
    role,
    status,
    password
)
VALUES (
    '11111111-1111-1111-1111-111111111111',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'user@gmail.com',
    'User Userson',
    'ROLE_USER',
    'ACTIVATED',
    '$2a$10$dcC2shTVRGnJ7P7GvXbtYeleEmA6FTcAGg1ksI1giBUfbHpSWHm9y'
);