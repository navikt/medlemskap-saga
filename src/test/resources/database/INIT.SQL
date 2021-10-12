CREATE TABLE vurdering
(
    id serial PRIMARY KEY,
    soknadId VARCHAR(255),
    date date,
    json jsonb
);