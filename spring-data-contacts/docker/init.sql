CREATE SCHEMA IF NOT EXISTS contact_schema;

CREATE TABLE IF NOT EXISTS contact_schema.contact
(
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(11) NOT NULL
)