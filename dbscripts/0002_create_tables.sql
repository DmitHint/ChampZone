CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

\c coach_service_db;

CREATE TABLE coach
(
    id         UUID PRIMARY KEY NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    email      VARCHAR(100) UNIQUE
);

\c training_service_db;

CREATE TABLE training
(
    id       UUID PRIMARY KEY NOT NULL,
    name     VARCHAR(100),
    coach_id UUID REFERENCES coach_service_db.coach (id)
);
