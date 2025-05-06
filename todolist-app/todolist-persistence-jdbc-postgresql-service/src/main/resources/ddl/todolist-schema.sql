-- DROP TABLE todolist.usertask;

CREATE SCHEMA IF NOT EXISTS todolist
    AUTHORIZATION postgres;

CREATE TABLE IF NOT EXISTS todolist.usertask
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    processinstanceid character varying(255) COLLATE pg_catalog."default" NOT NULL,
    processdefinitionid character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    actualowner character varying(255) COLLATE pg_catalog."default",
    inputs jsonb,
    CONSTRAINT usertask_pkey PRIMARY KEY (id, processinstanceid)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS todolist.usertask
    OWNER to postgres;

insert into usertask (id, processinstanceid, processdefinitionid, name, actualowner, potentialowners, inputs) 
	values ('1234', '3333', 'Tarjetas', 'User Task 1', 'Jaime', null, '{"Gender":"Male"}');

insert into usertask (id, processinstanceid, processdefinitionid, name, actualowner, potentialowners, inputs) 
	values ('1235', '3333', 'Tarjetas', 'User Task 1', 'Clara', null, '{"Gender":"Female"}');