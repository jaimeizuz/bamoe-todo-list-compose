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

create index if not exists idx_usertasks_tid on jbpm_user_tasks(user_task_id);
create index if not exists idx_jbpm_user_tasks_deadline_tid on jbpm_user_tasks_deadline(task_id);
create index if not exists idx_jbpm_user_tasks_inputs_tid on jbpm_user_tasks_inputs(task_id);
create index if not exists idx_jbpm_user_tasks_outputs_tid on jbpm_user_tasks_outputs(task_id);
create index if not exists idx_jbpm_user_tasks_metadata_tid on jbpm_user_tasks_metadata(task_id);
create index if not exists idx_jbpm_user_tasks_admin_groups_tid on jbpm_user_tasks_admin_groups(task_id);
create index if not exists idx_jbpm_user_tasks_admin_groups_gid on jbpm_user_tasks_admin_groups(group_id);
create index if not exists idx_jbpm_user_tasks_admin_users_tid on jbpm_user_tasks_admin_users(task_id);
create index if not exists idx_jbpm_user_tasks_admin_users_uid on jbpm_user_tasks_admin_users(user_id);
create index if not exists idx_jbpm_user_tasks_attachments_tid on jbpm_user_tasks_attachments(task_id);
create index if not exists idx_jbpm_user_tasks_potential_groups_tid on jbpm_user_tasks_potential_groups(task_id);
create index if not exists idx_jbpm_user_tasks_potential_groups_gid on jbpm_user_tasks_potential_groups(group_id);
create index if not exists idx_jbpm_user_tasks_potential_users_tid on jbpm_user_tasks_potential_users(task_id);
create index if not exists idx_jbpm_user_tasks_potential_users_uid on jbpm_user_tasks_potential_users(user_id);
create index if not exists idx_jbpm_user_tasks_excluded_users_uid on jbpm_user_tasks_excluded_users(user_id);
create index if not exists idx_jbpm_user_tasks_excluded_users_tid on jbpm_user_tasks_excluded_users(task_id);
