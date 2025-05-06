package org.jaime.kogito.tests.usertask.event.impl;

import org.kie.kogito.process.ProcessService;
import org.kie.kogito.usertask.UserTaskEventListener;
import org.kie.kogito.usertask.UserTaskService;
import org.kie.kogito.usertask.events.UserTaskAssignmentEvent;
import org.kie.kogito.usertask.events.UserTaskAttachmentEvent;
import org.kie.kogito.usertask.events.UserTaskCommentEvent;
import org.kie.kogito.usertask.events.UserTaskDeadlineEvent;
import org.kie.kogito.usertask.events.UserTaskStateEvent;
import org.kie.kogito.usertask.events.UserTaskVariableEvent;
import org.kie.kogito.usertask.impl.events.UserTaskAssignmentEventImpl;
import org.kie.kogito.usertask.impl.events.UserTaskAttachmentEventImpl;
import org.kie.kogito.usertask.impl.events.UserTaskCommentEventImpl;
import org.kie.kogito.usertask.impl.events.UserTaskDeadlineEventImpl;
import org.kie.kogito.usertask.impl.events.UserTaskStateEventImpl;
import org.kie.kogito.usertask.impl.events.UserTaskVariableEventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

public class CustomUserTaskEventListener implements UserTaskEventListener {

    /*
    @Inject
    UserTaskService userTaskService;

    @Inject
    ProcessService processService;
     */
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserTaskEventListener.class);

    public void onUserTaskDeadline(UserTaskDeadlineEvent event) {
        LOGGER.info("Calling onUserTaskState event");
        //UserTaskDeadlineEventImpl eventImpl = (UserTaskDeadlineEventImpl)event;
        //LOGGER.info("Executing onUserTaskDeadline event with data: " + eventImpl.toString());
    }

    public void onUserTaskState(UserTaskStateEvent event) {
        LOGGER.info("Calling onUserTaskState event");
        //UserTaskStateEventImpl eventImpl = (UserTaskStateEventImpl)event;
        //LOGGER.info("Executing onUserTaskState event with data: " + eventImpl.toString());
    }

    public void onUserTaskAssignment(UserTaskAssignmentEvent event) {
        LOGGER.info("Calling onUserTaskAssignment event");
        UserTaskAssignmentEventImpl eventImpl = (UserTaskAssignmentEventImpl)event;
        LOGGER.info("Executing onUserTaskAssignment event with data: " + eventImpl.toString());
    }

    public void onUserTaskInputVariable(UserTaskVariableEvent event) {
        LOGGER.info("Calling onUserTaskInputVariable event");
        UserTaskVariableEventImpl eventImpl = (UserTaskVariableEventImpl)event;
        LOGGER.info("Executing onUserTaskInputVariable event with data: " + eventImpl.toString());
    }

    public void onUserTaskOutputVariable(UserTaskVariableEvent event) {
        LOGGER.info("Calling onUserTaskOutputVariable event");
        UserTaskVariableEventImpl eventImpl = (UserTaskVariableEventImpl)event;
        LOGGER.info("Executing onUserTaskOutputVariable event with data: " + eventImpl.toString());
    }

    public void onUserTaskAttachmentAdded(UserTaskAttachmentEvent event) {
        LOGGER.info("Calling onUserTaskAttachmentAdded event");
        UserTaskAttachmentEventImpl eventImpl = (UserTaskAttachmentEventImpl)event;
        LOGGER.info("Executing onUserTaskAttachmentAdded event with data: " + eventImpl.toString());
    }

    public void onUserTaskAttachmentDeleted(UserTaskAttachmentEvent event) {
        LOGGER.info("Calling onUserTaskAttachmentDeleted event");
        UserTaskAttachmentEventImpl eventImpl = (UserTaskAttachmentEventImpl)event;
        LOGGER.info("Executing onUserTaskAttachmentDeleted event with data: " + eventImpl.toString());
    }

    public void onUserTaskAttachmentChange(UserTaskAttachmentEvent event) {
        LOGGER.info("Calling onUserTaskAttachmentChange event");
        UserTaskAttachmentEventImpl eventImpl = (UserTaskAttachmentEventImpl)event;
        LOGGER.info("Executing onUserTaskAttachmentChange event with data: " + eventImpl.toString());
    }

    public void onUserTaskCommentChange(UserTaskCommentEvent event) {
        LOGGER.info("Calling onUserTaskCommentChange event");
        UserTaskCommentEventImpl eventImpl = (UserTaskCommentEventImpl)event;
        LOGGER.info("Executing onUserTaskCommentChange event with data: " + eventImpl.toString());
    }

    public void onUserTaskCommentAdded(UserTaskCommentEvent event) {
        LOGGER.info("Calling onUserTaskCommentAdded event");
        UserTaskCommentEventImpl eventImpl = (UserTaskCommentEventImpl)event;
        LOGGER.info("Executing onUserTaskCommentAdded event with data: " + eventImpl.toString());
    }

    public void onUserTaskCommentDeleted(UserTaskCommentEvent event) {
        LOGGER.info("Calling onUserTaskCommentDeleted event");
        UserTaskCommentEventImpl eventImpl = (UserTaskCommentEventImpl)event;
        LOGGER.info("Executing onUserTaskCommentDeleted event with data: " + eventImpl.toString());
    }
}
