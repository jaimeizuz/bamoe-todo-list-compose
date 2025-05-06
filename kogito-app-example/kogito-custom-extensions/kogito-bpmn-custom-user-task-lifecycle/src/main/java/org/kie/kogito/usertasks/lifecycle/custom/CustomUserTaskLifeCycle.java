package org.kie.kogito.usertasks.lifecycle.custom;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.services.api.TodoListService;
import org.kie.kogito.auth.IdentityProvider;
import org.kie.kogito.usertask.UserTaskAssignmentStrategy;
import org.kie.kogito.usertask.UserTaskInstance;
import org.kie.kogito.usertask.UserTaskInstanceNotAuthorizedException;
import org.kie.kogito.usertask.impl.DefaultUserTaskInstance;
import org.kie.kogito.usertask.impl.lifecycle.DefaultUserTaskTransitionToken;
import org.kie.kogito.usertask.impl.lifecycle.DefaultUserTransition;
import org.kie.kogito.usertask.lifecycle.UserTaskLifeCycle;
import org.kie.kogito.usertask.lifecycle.UserTaskState;
import org.kie.kogito.usertask.lifecycle.UserTaskState.TerminationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.kie.kogito.usertask.lifecycle.UserTaskTransition;
import org.kie.kogito.usertask.lifecycle.UserTaskTransitionException;
import org.kie.kogito.usertask.lifecycle.UserTaskTransitionToken;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class CustomUserTaskLifeCycle implements UserTaskLifeCycle {

    Logger logger = LoggerFactory.getLogger(CustomUserTaskLifeCycle.class);

    public static final String WORKFLOW_ENGINE_USER = "WORKFLOW_ENGINE_USER";

    public static final String PARAMETER_USER = "USER";
    public static final String PARAMETER_NOTIFY = "NOTIFY";

    public static final String ACTIVATE = "activate";
    public static final String CLAIM = "claim";
    public static final String RELEASE = "release";
    public static final String START = "start";
    public static final String PAUSE = "pause";
    public static final String RESUME = "resume";
    public static final String COMPLETE = "complete";
    public static final String SKIP = "skip";
    public static final String FAIL = "fail";
    public static final String REASSIGN = "reassign";

    public static final UserTaskState INACTIVE = UserTaskState.initalized();
    public static final UserTaskState ACTIVE = UserTaskState.of("Ready");
    public static final UserTaskState RESERVED = UserTaskState.of("Reserved");
    public static final UserTaskState INPROGRESS = UserTaskState.of("In Progress");
    public static final UserTaskState COMPLETED = UserTaskState.of("Completed", TerminationType.COMPLETED);
    public static final UserTaskState PAUSED = UserTaskState.of("Paused");
    public static final UserTaskState ERROR = UserTaskState.of("Error", TerminationType.ERROR);
    public static final UserTaskState OBSOLETE = UserTaskState.of("Obsolete", TerminationType.OBSOLETE);

    private final UserTaskTransition T_NEW_ACTIVE = new DefaultUserTransition(ACTIVATE, INACTIVE, ACTIVE, this::activate);
    private final UserTaskTransition T_ACTIVE_RESERVED = new DefaultUserTransition(CLAIM, ACTIVE, RESERVED, this::claim);
    private final UserTaskTransition T_ACTIVE_SKIPPED = new DefaultUserTransition(SKIP, ACTIVE, OBSOLETE, this::skip);
    private final UserTaskTransition T_ACTIVE_ERROR = new DefaultUserTransition(FAIL, ACTIVE, ERROR, this::fail);
    private final UserTaskTransition T_RESERVED_ACTIVE = new DefaultUserTransition(RELEASE, RESERVED, ACTIVE, this::release);
    private final UserTaskTransition T_RESERVED_INPROGRESS = new DefaultUserTransition(START, RESERVED, INPROGRESS, this::start);
    private final UserTaskTransition T_INPROGRESS_RESERVED = new DefaultUserTransition(RELEASE, INPROGRESS, RESERVED, this::release);
    private final UserTaskTransition T_INPROGRESS_PAUSED = new DefaultUserTransition(PAUSE, INPROGRESS, PAUSED, this::pause);
    private final UserTaskTransition T_INPROGRESS_ERROR = new DefaultUserTransition(FAIL, INPROGRESS, ERROR, this::fail);
    private final UserTaskTransition T_INPROGRESS_SKIPPED = new DefaultUserTransition(SKIP, INPROGRESS, OBSOLETE, this::skip);
    private final UserTaskTransition T_INPROGRESS_COMPLETED = new DefaultUserTransition(COMPLETE, INPROGRESS, COMPLETED, this::complete);
    private final UserTaskTransition T_PAUSED_INPROGRESS = new DefaultUserTransition(RESUME, PAUSED, INPROGRESS, this::resume);
    private final UserTaskTransition T_PAUSED_ERROR = new DefaultUserTransition(FAIL, PAUSED, ERROR, this::fail);
    private final UserTaskTransition T_PAUSED_SKIPPED = new DefaultUserTransition(SKIP, PAUSED, OBSOLETE, this::skip);
    private final UserTaskTransition T_RESERVED_COMPLETED = new DefaultUserTransition(COMPLETE, RESERVED, COMPLETED, this::complete);
    private final UserTaskTransition T_RESERVED_SKIPPED = new DefaultUserTransition(SKIP, RESERVED, OBSOLETE, this::skip);
    private final UserTaskTransition T_RESERVED_ERROR = new DefaultUserTransition(FAIL, RESERVED, ERROR, this::fail);

    private final UserTaskTransition T_RESERVED_ACTIVE_R = new DefaultUserTransition(REASSIGN, RESERVED, ACTIVE, this::reassign);
    private final UserTaskTransition T_ACTIVE_ACTIVE_R = new DefaultUserTransition(REASSIGN, ACTIVE, ACTIVE, this::reassign);

    private List<UserTaskTransition> transitions;

    @Inject
    TodoListService todoListService;

    ObjectMapper mapper = new ObjectMapper();

    public CustomUserTaskLifeCycle() {
        logger.debug("================================================");
        logger.debug("================================================");
        logger.debug("CREATING CUSTOM UT LIFECYCLE");
        logger.debug("================================================");
        logger.debug("================================================");
        transitions = List.of(
                T_NEW_ACTIVE,
                T_ACTIVE_RESERVED,
                T_ACTIVE_SKIPPED,
                T_ACTIVE_ERROR,
                T_RESERVED_ACTIVE,
                T_RESERVED_INPROGRESS,
                T_INPROGRESS_RESERVED,
                T_INPROGRESS_PAUSED,
                T_INPROGRESS_ERROR,
                T_INPROGRESS_SKIPPED,
                T_INPROGRESS_COMPLETED,
                T_PAUSED_INPROGRESS,
                T_PAUSED_ERROR,
                T_PAUSED_SKIPPED,
                T_RESERVED_COMPLETED,
                T_RESERVED_SKIPPED,
                T_RESERVED_ERROR,
                T_RESERVED_ACTIVE_R,
                T_ACTIVE_ACTIVE_R);
    }

    // Overridable since 999-202503x
    @Override
    public List<UserTaskTransition> allowedTransitions(UserTaskInstance userTaskInstance, IdentityProvider identityProvider) {
        checkPermission(userTaskInstance, identityProvider);
        return transitions.stream().filter(t -> t.source().equals(userTaskInstance.getStatus())).toList();
    }

    //@Override
    public List<UserTaskTransition> allowedTransitions(UserTaskInstance userTaskInstance) {
        return transitions.stream().filter(t -> t.source().equals(userTaskInstance.getStatus())).toList();
    }

    @Override
    public Optional<UserTaskTransitionToken> transition(UserTaskInstance userTaskInstance, UserTaskTransitionToken userTaskTransitionToken, IdentityProvider identityProvider) {
        logger.debug("================================================");
        logger.debug("================================================");
        logger.debug("TRANSITION TRIGGERED!! --> " + userTaskTransitionToken.transitionId());
        logger.debug("================================================");
        logger.debug("================================================");
        
        checkPermission(userTaskInstance, identityProvider);
        UserTaskTransition transition = transitions.stream()
                .filter(t -> t.source().equals(userTaskInstance.getStatus()) && t.id().equals(userTaskTransitionToken.transitionId()))
                .findFirst()
                .orElseThrow(() -> new UserTaskTransitionException("Invalid transition from " + userTaskInstance.getStatus()));
        return transition.executor().execute(userTaskInstance, userTaskTransitionToken, identityProvider);
    }

    @Override
    public Optional<UserTaskTransitionToken> newReassignmentTransitionToken(UserTaskInstance userTaskInstance, Map<String, Object> data) {
        try {
            return Optional.of(newTransitionToken(REASSIGN, userTaskInstance.getStatus(), data));
        } catch (UserTaskTransitionException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserTaskTransitionToken newCompleteTransitionToken(UserTaskInstance userTaskInstance, Map<String, Object> data) {
        return newTransitionToken(COMPLETE, userTaskInstance.getStatus(), data);
    }

    @Override
    public UserTaskTransitionToken newAbortTransitionToken(UserTaskInstance userTaskInstance, Map<String, Object> data) {
        return newTransitionToken(FAIL, userTaskInstance.getStatus(), data);
    }

    @Override
    public UserTaskTransitionToken newTransitionToken(String transitionId, UserTaskInstance userTaskInstance, Map<String, Object> data) {
        return newTransitionToken(transitionId, userTaskInstance.getStatus(), data);
    }

    public UserTaskTransitionToken newTransitionToken(String transitionId, UserTaskState state, Map<String, Object> data) {
        UserTaskTransition transition = transitions.stream().filter(e -> e.source().equals(state) && e.id().equals(transitionId)).findAny()
                .orElseThrow(() -> new RuntimeException("Invalid transition " + transitionId + " from " + state));
        return new DefaultUserTaskTransitionToken(transition.id(), transition.source(), transition.target(), data);
    }

    public Optional<UserTaskTransitionToken> reassign(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        userTaskInstance.stopNotStartedDeadlines();
        userTaskInstance.stopNotStartedReassignments();
        userTaskInstance.stopNotCompletedDeadlines();
        userTaskInstance.stopNotCompletedReassignments();

        // restart the timers
        userTaskInstance.startNotCompletedDeadlines();
        userTaskInstance.startNotCompletedReassignments();

        String user = assignStrategy(userTaskInstance, identityProvider);
        if (user != null) {
            return Optional.of(newTransitionToken(CLAIM, ACTIVE, Map.of(PARAMETER_USER, user)));
        }
        userTaskInstance.startNotStartedDeadlines();
        userTaskInstance.startNotStartedReassignments();
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> activate(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        userTaskInstance.startNotCompletedDeadlines();
        userTaskInstance.startNotCompletedReassignments();

        String user = assignStrategy(userTaskInstance, identityProvider);
        if (user != null) {
            return Optional.of(newTransitionToken(CLAIM, ACTIVE, Map.of(PARAMETER_USER, user)));
        }
        userTaskInstance.startNotStartedDeadlines();
        userTaskInstance.startNotStartedReassignments();

        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> claim(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        String userId = "";
        if (userTaskInstance instanceof DefaultUserTaskInstance defaultUserTaskInstance) {
            if (token.data().containsKey(PARAMETER_USER)) {
                userId = (String) token.data().get(PARAMETER_USER);
            } else {
                userId = identityProvider.getName();
            }

            defaultUserTaskInstance.setActualOwner(userId);
        }

        try {
            todoListService.createUserTask(new UserTask(
                userTaskInstance.getId(),
                (String)userTaskInstance.getMetadata().get("ProcessInstanceId"),
                (String)userTaskInstance.getMetadata().get("ProcessId"),
                userTaskInstance.getTaskName(),
                userId,
                mapper.writeValueAsString(userTaskInstance.getInputs())));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        userTaskInstance.stopNotStartedDeadlines();
        userTaskInstance.stopNotStartedReassignments();
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> release(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        if (userTaskInstance instanceof DefaultUserTaskInstance defaultUserTaskInstance) {
            defaultUserTaskInstance.setActualOwner(null);
        }
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> start(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        
        return Optional.empty();
    }
    
    public Optional<UserTaskTransitionToken> resume(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> pause(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> complete(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        token.data().forEach(userTaskInstance::setOutput);
        userTaskInstance.stopNotStartedDeadlines();
        userTaskInstance.stopNotStartedReassignments();
        userTaskInstance.stopNotCompletedDeadlines();
        userTaskInstance.stopNotCompletedReassignments();
        
        todoListService.deleteUserTask(userTaskInstance.getId());
        
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> skip(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        if (token.data().containsKey(PARAMETER_NOTIFY)) {
            userTaskInstance.getMetadata().put(PARAMETER_NOTIFY, token.data().get(PARAMETER_NOTIFY));
        }
        userTaskInstance.stopNotStartedDeadlines();
        userTaskInstance.stopNotStartedReassignments();
        userTaskInstance.stopNotCompletedDeadlines();
        userTaskInstance.stopNotCompletedReassignments();
        return Optional.empty();
    }

    public Optional<UserTaskTransitionToken> fail(UserTaskInstance userTaskInstance, UserTaskTransitionToken token, IdentityProvider identityProvider) {
        if (token.data().containsKey(PARAMETER_NOTIFY)) {
            userTaskInstance.getMetadata().put(PARAMETER_NOTIFY, token.data().get(PARAMETER_NOTIFY));
        }
        userTaskInstance.stopNotStartedDeadlines();
        userTaskInstance.stopNotStartedReassignments();
        userTaskInstance.stopNotCompletedDeadlines();
        userTaskInstance.stopNotCompletedReassignments();
        return Optional.empty();
    }

    private String assignStrategy(UserTaskInstance userTaskInstance, IdentityProvider identityProvider) {
        UserTaskAssignmentStrategy assignmentStrategy = userTaskInstance.getUserTask().getAssignmentStrategy();
        return assignmentStrategy.computeAssignment(userTaskInstance, identityProvider).orElse(null);
    }

    private void checkPermission(UserTaskInstance userTaskInstance, IdentityProvider identityProvider) {
        String user = identityProvider.getName();
        Collection<String> roles = identityProvider.getRoles();

        if (WORKFLOW_ENGINE_USER.equals(user)) {
            return;
        }

        // first we check admins
        Set<String> adminUsers = userTaskInstance.getAdminUsers();
        if (adminUsers.contains(user)) {
            return;
        }

        Set<String> userAdminGroups = new HashSet<>(userTaskInstance.getAdminGroups());
        userAdminGroups.retainAll(roles);
        if (!userAdminGroups.isEmpty()) {
            return;
        }

        if (userTaskInstance.getActualOwner() != null && userTaskInstance.getActualOwner().equals(user)) {
            return;
        }

        if (List.of(INACTIVE, ACTIVE).contains(userTaskInstance.getStatus())) {
            // there is no user
            Set<String> users = new HashSet<>(userTaskInstance.getPotentialUsers());
            users.removeAll(userTaskInstance.getExcludedUsers());
            if (users.contains(identityProvider.getName())) {
                return;
            }

            Set<String> userPotGroups = new HashSet<>(userTaskInstance.getPotentialGroups());
            userPotGroups.retainAll(roles);
            if (!userPotGroups.isEmpty()) {
                return;
            }
        }

        throw new UserTaskInstanceNotAuthorizedException("user " + user + " with roles " + roles + " not autorized to perform an operation on user task " + userTaskInstance.getId());
    }
}
