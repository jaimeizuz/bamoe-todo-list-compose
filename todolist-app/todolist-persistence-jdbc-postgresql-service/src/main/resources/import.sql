-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;
create table UserTask (
        id varchar(255) not null,
        processInstanceId varchar(255) not null,
        processDefinitionId varchar(255),
        name varchar(255),
        actualOwner varchar(255),
        inputs jsonb,
        primary key (id, processInstanceId)
    );

insert into usertask (id, name, processdefinitionid, processinstanceid, actualowner, potentialowners, inputs) 
	values ('1234', 'User Task 1', 'Tarjetas', '3333', 'Jaime', '{"var1":"value1"}');