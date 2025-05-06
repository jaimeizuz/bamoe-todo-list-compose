package org.kie.kogito.hr.workitemhandlers.config;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.kie.kogito.hr.rest.client.DummyRestClient;
import org.kie.kogito.hr.workitemhandlers.impl.DummyWorkItemHandler;
import org.kie.kogito.process.impl.DefaultWorkItemHandlerConfig;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CustomWorkItemHandlerConfig extends DefaultWorkItemHandlerConfig {

    @Inject
    @RestClient
    DummyRestClient dummyRestClient;

    {
        register("CustomTask", new DummyWorkItemHandler(dummyRestClient));
    }
}