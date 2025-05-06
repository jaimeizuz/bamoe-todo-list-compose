package org.jaime.tests.todolist.services;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.entities.UserTasks;
import org.jaime.tests.todolist.services.api.TodoListService;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KafkaTodoListEmitterService implements TodoListService {

    @Inject
    @Channel("todolist")
    Emitter<UserTask> userTaskEmitter;

    @Override
    public UserTask findUserTaskById(String userTaskId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTaskById'");
    }

    @Override
    public UserTasks findUserTasksByInputValue(String inputName, String inputValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTasksByInputValue'");
    }

    @Override
    public UserTasks findUserTasksByActualOwnerAndInputValue(String userId, String inputName, String inputValue) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTasksByActualOwnerAndInputValue'");
    }

    @Override
    public UserTasks findUserTasksByActualOwner(String userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findUserTasksByActualOwner'");
    }

    @Override
    public Boolean createUserTask(UserTask userTask) {
        userTaskEmitter.send(
            Message.of(userTask)
                .addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                        .withHeaders(new RecordHeaders().add("operation", "create".getBytes()))
                        .build()));

        return true;
    }

    @Override
    public Boolean deleteUserTask(String id) {
        UserTask userTask = new UserTask();
        userTask.setId(id);

        userTaskEmitter.send(
            Message.of(userTask)
                .addMetadata(OutgoingKafkaRecordMetadata.<String>builder()
                        .withHeaders(new RecordHeaders().add("operation", "delete".getBytes()))
                        .build()));

        return true;
    }
}
