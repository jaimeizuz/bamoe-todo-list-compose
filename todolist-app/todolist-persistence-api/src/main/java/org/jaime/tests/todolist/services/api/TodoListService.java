package org.jaime.tests.todolist.services.api;

import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.entities.UserTasks;

public interface TodoListService {

    public UserTask findUserTaskById(String userTaskId);

    public UserTask findUserTaskByProcessInstanceId(String userTaskId);

    public UserTasks findUserTasksByInputValue(String inputName, String inputValue);

    public UserTasks findUserTasksByActualOwnerAndInputValue(String userId, String inputName, String inputValue);

    public UserTasks findUserTasksByActualOwner(String userId);
    
    public Boolean createUserTask(UserTask userTask);

    public Boolean deleteUserTask(String id);
}
