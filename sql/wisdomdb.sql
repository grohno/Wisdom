CREATE DATABASE wisdomdb;
\c wisdomdb;

CREATE TABLE users (
    id serial PRIMARY KEY,
    name character varying(30) NOT NULL ,
    email character varying(50) NOT NULL UNIQUE,
    encrypt_password character varying(255) NOT NULL,
    salt character varying(255) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL
);
CREATE TABLE questions (
    id serial PRIMARY KEY,
    title character varying(50) NOT NULL,
    content character varying(1000) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    create_user_id integer REFERENCES users(id)
);
CREATE TABLE answers (
    id serial PRIMARY KEY,
    content character varying(1000) NOT NULL,
    created_at timestamp with time zone NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    question_id integer NOT NULL REFERENCES questions(id),
    create_user_id integer REFERENCES users(id)
);
