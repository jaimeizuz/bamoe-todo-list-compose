package org.jaime.kogito.tests.usertask.event.config;

import java.util.Arrays;
import java.util.List;

import org.jaime.kogito.tests.usertask.event.impl.CustomUserTaskEventListener;
import org.kie.kogito.usertask.UserTaskEventListener;
import org.kie.kogito.usertask.UserTaskEventListenerConfig;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomUserTaskEventListenerConfig implements UserTaskEventListenerConfig {

    @Override
    public List<UserTaskEventListener> listeners() {
        return Arrays.asList(new CustomUserTaskEventListener());
    }
    
}
