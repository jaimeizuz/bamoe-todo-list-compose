CREATE ROLE "todolist-user" WITH
    LOGIN
    SUPERUSER
    INHERIT
    CREATEDB
    CREATEROLE
    NOREPLICATION
    PASSWORD 'todolist-pass';

CREATE DATABASE "todolist"
    WITH
    OWNER = "todolist-user"
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE todolist TO "todolist-user";
GRANT ALL PRIVILEGES ON DATABASE todolist TO postgres;

\connect todolist;

CREATE TABLE IF NOT EXISTS usertask
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    processinstanceid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    processdefinitionid character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    actualowner character varying(255) COLLATE pg_catalog."default",
    inputs jsonb,
    CONSTRAINT usertask_pkey PRIMARY KEY (id, processinstanceid)
)