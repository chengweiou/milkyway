set search_path = milkyway;

DROP TABLE IF EXISTS person;
CREATE TABLE person(
    id bigserial NOT NULL,
    name text NOT NULL,
    type text NOT NULL,
    phone text NOT NULL,
    createAt timestamp with time zone NOT NULL,
    updateAt timestamp with time zone NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS pet;
CREATE TABLE pet(
    id bigserial NOT NULL,
    personId integer NOT NULL,
    name text NOT NULL,
    type text NOT NULL,
    age integer NOT NULL,
    createAt timestamp with time zone NOT NULL,
    updateAt timestamp with time zone NOT NULL,
    PRIMARY KEY (id)
);
