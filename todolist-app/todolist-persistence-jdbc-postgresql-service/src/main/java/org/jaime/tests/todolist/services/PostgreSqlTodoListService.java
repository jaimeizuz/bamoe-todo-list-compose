package org.jaime.tests.todolist.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.entities.UserTasks;
import org.jaime.tests.todolist.services.api.TodoListService;
import org.postgresql.util.PGobject;

import io.agroal.api.AgroalDataSource;
import io.quarkus.agroal.DataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

@ApplicationScoped
public class PostgreSqlTodoListService implements TodoListService {

    static final String insertNewUserTask = "INSERT INTO USERTASK (id, processinstanceid, processdefinitionid, name, actualowner, inputs) VALUES (?, ?, ?, ?, ?, ?)";
    static final String deleteUserTask = "DELETE FROM USERTASK WHERE id = ?";
    static final String getUserTaskById = "SELECT * FROM USERTASK WHERE id = ?";
    static final String findUserTasksByInput = "SELECT * FROM USERTASK WHERE inputs ->>'param' = ?";
    static final String findUserTasksByActualOwner = "SELECT * FROM USERTASK WHERE actualowner = ?";
    static final String findUserTasksByActualOwnerAndInput = "SELECT * FROM USERTASK WHERE actualowner = ? and inputs ->>'param' = ?";

    @Inject
    @DataSource("todolist")
    AgroalDataSource dataSource;
    
    @Override
    @Transactional(value = TxType.REQUIRED)
    public UserTask findUserTaskById(String userTaskId) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(getUserTaskById)) {
            statement.setString(1, userTaskId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new UserTask(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        ((PGobject)resultSet.getObject(6)).getValue()
                        );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new UserTask();
    }

    @Override
    @Transactional(value = TxType.REQUIRED)
    public UserTasks findUserTasksByInputValue(String inputName, String inputValue) {

        UserTasks userTasksList = new UserTasks();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(findUserTasksByInput.replace("param", inputName))) {
            statement.setString(1, inputValue);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    userTasksList.addUserTask(
                        new UserTask(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            ((PGobject)resultSet.getObject(6)).getValue()
                        ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return userTasksList;
    }

    @Override
    @Transactional(value = TxType.REQUIRED)
    public UserTasks findUserTasksByActualOwnerAndInputValue(String userId, String inputName, String inputValue) {

        UserTasks userTasksList = new UserTasks();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(findUserTasksByActualOwnerAndInput.replace("param", inputName))) {
            statement.setString(1, userId);
            statement.setString(2, inputValue);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    userTasksList.addUserTask(
                        new UserTask(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            ((PGobject)resultSet.getObject(6)).getValue()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userTasksList;
    }

    @Override
    @Transactional(value = TxType.REQUIRED)
    public UserTasks findUserTasksByActualOwner(String userId) {

        UserTasks userTasksList = new UserTasks();

        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(findUserTasksByActualOwner)) {
            statement.setString(1, userId);
            
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    userTasksList.addUserTask(
                        new UserTask(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            ((PGobject)resultSet.getObject(6)).getValue()
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userTasksList;
    }

    @Override
    @Transactional(value = TxType.REQUIRED)
    public Boolean createUserTask(UserTask userTask) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(insertNewUserTask)) {
            statement.setString(1, userTask.getId());
            statement.setString(2, userTask.getProcessInstanceId());
            statement.setString(3, userTask.getProcessDefinitionId());
            statement.setString(4, userTask.getName());
            statement.setString(5, userTask.getActualOwner());

            // Opt 1: OTHER type
            statement.setObject(6, userTask.getInputs(), java.sql.Types.OTHER);

            // Opt 2: Custom PGObject
            /*
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(userTask.getInputs());
            statement.setObject(6, jsonObject);
             */

             int result = statement.executeUpdate();
             
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    @Transactional(value = TxType.REQUIRED)
    public Boolean deleteUserTask(String id) {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(deleteUserTask)) {
            
            statement.setString(1, id);

             int result = statement.executeUpdate();
             if(result > 0) {
                return true;
             }
        } catch (SQLException e) {
           throw new RuntimeException(e);
        }

        return false;
    }
}
