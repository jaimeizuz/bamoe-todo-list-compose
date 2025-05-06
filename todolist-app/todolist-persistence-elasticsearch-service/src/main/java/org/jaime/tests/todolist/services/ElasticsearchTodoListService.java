package org.jaime.tests.todolist.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.entities.UserTasks;
import org.jaime.tests.todolist.services.api.TodoListService;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ElasticsearchTodoListService implements TodoListService {

    @Inject
    RestClient restClient;

    @Override
    public UserTask findUserTaskById(String userTaskId) {
        Request request = new Request(
                "GET",
                "/usertask/_doc/" + userTaskId);
                
        try {
            Response response = restClient.performRequest(request);
            String responseBody;
                responseBody = EntityUtils.toString(response.getEntity());
            
            JsonObject json = new JsonObject(responseBody);
            return json.getJsonObject("_source").mapTo(UserTask.class);
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }        
    }

    @Override
    public UserTasks findUserTasksByInputValue(String inputName, String inputValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTaskByInputValue'");
    }

    @Override
    public UserTasks findUserTasksByActualOwnerAndInputValue(String userId, String inputName, String inputValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTaskByActualOwnerAndInputValue'");
    }

    @Override
    public UserTasks findUserTasksByActualOwner(String userId) {

        UserTasks userTasks = new UserTasks();

        Request request = new Request(
                "GET",
                "/usertask/_search");
        //construct a JSON query like {"query": {"match": {"<term>": "<match"}}
        JsonObject termJson = new JsonObject().put("actualOwner", userId);
        JsonObject matchJson = new JsonObject().put("match", termJson);
        JsonObject queryJson = new JsonObject().put("query", matchJson);
        request.setJsonEntity(queryJson.encode());
        Response response;
        try {
            response = restClient.performRequest(request);
            String responseBody = EntityUtils.toString(response.getEntity());
            
            JsonObject json = new JsonObject(responseBody);
            JsonArray hits = json.getJsonObject("hits").getJsonArray("hits");

            for (int i = 0; i < hits.size(); i++) {
                JsonObject hit = hits.getJsonObject(i);
                UserTask userTask = hit.getJsonObject("_source").mapTo(UserTask.class);
                userTasks.addUserTask(userTask);
            }

            return userTasks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean createUserTask(UserTask userTask) {
        Request request = new Request(
                "PUT",
                "/usertask/_doc/" + userTask.getId());
        request.setJsonEntity(JsonObject.mapFrom(userTask).toString());

        try {
            restClient.performRequest(request);

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean deleteUserTask(String id) {

        var entityList = new ArrayList<JsonObject>();

        entityList.add(new JsonObject().put("delete",
                new JsonObject().put("_index", "usertask").put("_id", id)));

        Request request = new Request(
                "POST", "usertask/_bulk?pretty");
        request.setEntity(new StringEntity(
                toNdJsonString(entityList),
                ContentType.create("application/x-ndjson")));
        try {
            restClient.performRequest(request);

            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toNdJsonString(List<JsonObject> objects) {
        return objects.stream()
                .map(JsonObject::encode)
                .collect(Collectors.joining("\n", "", "\n"));
    }
}
