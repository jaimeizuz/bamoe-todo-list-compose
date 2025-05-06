package org.jaime.tests.todolist.entities;

public class UserTask {
    public String id;
    public String processInstanceId;
    public String processDefinitionId;
    public String name;
    public String actualOwner;
    public String inputs;

    public UserTask() {}

    public UserTask(String id, String processInstanceId, String processDefinitionId, String name, String actualOwner, String inputs) {
        this.id = id;
        this.processInstanceId = processInstanceId;
        this.processDefinitionId = processDefinitionId;
        this.name = name;
        this.actualOwner = actualOwner;
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
    public String getInputs() {
        return inputs;
    }

    public void setInputs(String inputs) {
        this.inputs = inputs;
    }

    @Override
    public String toString() {
        return "UserTask [id=" + id + ", processInstanceId=" + processInstanceId + ", processDefinitionId="
                + processDefinitionId + ", name=" + name + ", actualOwner=" + actualOwner + ", " + "inputs=" + inputs + "]";
    }
}
