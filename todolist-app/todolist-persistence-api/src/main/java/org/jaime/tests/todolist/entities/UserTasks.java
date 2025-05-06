package org.jaime.tests.todolist.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserTasks implements Serializable {
    
    private List<UserTask> userTasksList;

    public UserTasks(){
        this.userTasksList = new ArrayList<>();
    }

    public UserTasks(List<UserTask> userTasksList) {
        this.userTasksList = userTasksList;
    }

    public List<UserTask> getUserTasksList() {
        return userTasksList;
    }

    public void setUserTasksList(List<UserTask> userTasksList) {
        this.userTasksList = userTasksList;
    }

    public void addUserTask(UserTask userTask) {
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
