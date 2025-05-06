create schema todolist authorization postgres;

set schema 'todolist';

create table UserTask (
        id varchar(255) not null,
        processInstanceId varchar(255) not null,
        processDefinitionId varchar(255),
        name varchar(255),
        actualOwner varchar(255),
        inputs jsonb,
        primary key (id, processInstanceId)
    );

insert into UserTask (id, name, processdefinitionid, processinstanceid, actualowner, inputs) 
	values ('1234', 'User Task 1', 'Tarjetas', '3333', 'Jaime', '{"var1":"value1"}');

select * from UserTask;
select * from kogito.jbpm_user_tasks;
select * from kogito.jbpm_user_tasks_metadata;