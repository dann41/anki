CREATE TABLE authentication
(
    user_id VARCHAR NOT NULL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    payload JSONB NOT NULL,
    payload_version NUMERIC
);

CREATE TABLE deck
(
    id VARCHAR PRIMARY KEY,
    user_id VARCHAR NOT NULL,
    collection_id VARCHAR NOT NULL,
    payload JSONB NOT NULL,
    payload_version NUMERIC
);

CREATE TABLE collections
(
    id VARCHAR PRIMARY KEY,
    payload JSONB NOT NULL,
    payload_version NUMERIC
);