package org.jaime.tests.todolist.services;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.services.api.TodoListService;

import io.smallrye.reactive.messaging.kafka.IncomingKafkaRecord;
import io.smallrye.reactive.messaging.kafka.api.IncomingKafkaRecordMetadata;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class KafkaTodoListProcessorService {

    @Inject
    TodoListService todoListService;

    /*
    @Incoming("todolist")
    public CompletionStage<Void> consume(Message<UserTask> msg) {
        // access record metadata
        var headers = msg.getPayload();
        // process the message payload.
        UserTask userTask = msg.getPayload();
        // Acknowledge the incoming message (commit the offset)
        return msg.ack();
    }
    */

    @Incoming("todolist")
    public void consumer(ConsumerRecord<String, UserTask> message) {
        // access record metadata
        var headers = message.headers();

        for (Header header : headers) {
            if(header.key().equals("operation")) {
                String operation = new String(header.value(), StandardCharsets.UTF_8);

                System.out.println("OPERATION VALUE: " + operation);

                if(operation.equals("create")) {
                    todoListService.createUserTask(message.value());
                }
                else if(operation.equals("delete")) {
                    todoListService.deleteUserTask(message.value().getId());
                }
            }
        }
    }
}
