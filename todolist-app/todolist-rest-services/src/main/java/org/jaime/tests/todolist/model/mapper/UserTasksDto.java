package org.jaime.tests.todolist.model.mapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jaime.tests.todolist.model.UserTaskDto;

public class UserTasksDto implements Serializable {

    private List<UserTaskDto> userTasksList;

    public UserTasksDto(){
        this.userTasksList = new ArrayList<>();
    }

    public UserTasksDto(List<UserTaskDto> userTasksList) {
        this.userTasksList = userTasksList;
    }

    public List<UserTaskDto> getUserTasksList() {
        return userTasksList;
    }

    public void setUserTasksList(List<UserTaskDto> userTasksList) {
        this.userTasksList = userTasksList;
    }

    public void addUserTask(UserTaskDto userTask) {
        if(userTasksList == null) {
            userTasksList = new ArrayList<>();
        }

        userTasksList.add(userTask);
    }

    @Override
    public String toString() {
        return "UserTasks [userTasksList=" + userTasksList + "]";
    }
    
}
