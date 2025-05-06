package org.jaime.tests.todolist.resource;

import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.entities.UserTasks;
import org.jaime.tests.todolist.model.UserTaskDto;
import org.jaime.tests.todolist.model.mapper.UserTaskMapper;
import org.jaime.tests.todolist.services.api.TodoListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/tasks")
public class TodoListResource {

    Logger logger = LoggerFactory.getLogger(TodoListResource.class);

    @Inject
    TodoListService todoListService;

    @Inject
    UserTaskMapper userTaskMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTasksByFilters(@QueryParam("user-id")String userId, @QueryParam("input-name")String inputName, @QueryParam("input-value")String inputValue) {
        
        UserTasks ut = new UserTasks();

        if(userId == null && inputName == null) {
            return Response.status(Status.BAD_REQUEST).build();
        } else if(userId == null) {
            logger.info("findUserTaskByInputValue");
            ut = todoListService.findUserTasksByInputValue(inputName, inputValue);
        }
        else if (inputName == null) {
            logger.info("findUserTaskByActualOwner");
            ut = todoListService.findUserTasksByActualOwner(userId);
        }
        else {
            logger.info("else");
            ut = todoListService.findUserTasksByActualOwnerAndInputValue(userId, inputName, inputValue);
        }

        if(ut.getUserTasksList().size() == 0) {
            return Response.status(Status.NOT_FOUND).build();
        }
        
        
        return Response.ok(userTaskMapper.toResource(ut)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user-task-id}")
    public Response getTaskByUserTaskId(@PathParam("user-task-id")String userTaskId) {

        UserTask ut = todoListService.findUserTaskById(userTaskId);
        System.out.println(ut.id);
        System.out.println(ut.inputs);
        
        return Response.ok(userTaskMapper.toResource(ut)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUserTask(UserTaskDto userTaskDto) {

        todoListService.createUserTask(userTaskMapper.toEntity(userTaskDto));
        
        return Response.created(null).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user-task-id}")
    public Response deleteUserTask(@PathParam("user-task-id")String id) {

        todoListService.deleteUserTask(id);
        
        return Response.ok().build();
    }
}
