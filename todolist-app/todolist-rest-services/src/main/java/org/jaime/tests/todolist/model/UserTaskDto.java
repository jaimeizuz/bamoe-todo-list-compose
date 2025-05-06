package org.jaime.tests.todolist.model;

import java.io.Serializable;
import java.util.Arrays;

public class UserTaskDto implements Serializable {
    public String id;
    public String processInstanceId;
    public String processDefinitionId;
    public String name;
    public String actualOwner;
    public String[] potentialOwners;
    public String inputs;

    public UserTaskDto() {}

    public UserTaskDto(String id, String processInstanceId, String processDefinitionId, String name, String actualOwner, String[] potentialOwners, String inputs) {
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.processDefinitionId = processDefinitionId;
        this.name = name;
        this.actualOwner = actualOwner;
        this.potentialOwners = potentialOwners;
        this.inputs = inputs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActualOwner() {
        return actualOwner;
    }

    public void setActualOwner(String actualOwner) {
        this.actualOwner = actualOwner;
    }

    public String[] getPotentialOwners() {
        return potentialOwners;
    }

    public void setPotentialOwners(String[] potentialOwners) {
        this.potentialOwners = potentialOwners;
    }

    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    @Override
    public String toString() {
        return "UserTaskDto [id=" + id + ", processInstanceId=" + processInstanceId + ", processDefinitionId="
                + processDefinitionId + ", name=" + name + ", actualOwner=" + actualOwner + ", potentialOwners="
                + Arrays.toString(potentialOwners) + ", inputs=" + inputs + "]";
    }
}
