package org.kie.kogito.hr.workitemhandlers.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.kie.api.runtime.process.ProcessWorkItemHandlerException;
import org.kie.api.runtime.process.ProcessWorkItemHandlerException.HandlingStrategy;
import org.kie.kogito.hr.rest.client.DummyRestClient;
import org.kie.kogito.internal.process.workitem.KogitoWorkItem;
import org.kie.kogito.internal.process.workitem.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.workitem.KogitoWorkItemManager;
import org.kie.kogito.internal.process.workitem.Policy;
import org.kie.kogito.internal.process.workitem.WorkItemExecutionException;
import org.kie.kogito.internal.process.workitem.WorkItemHandlerRuntimeException;
import org.kie.kogito.internal.process.workitem.WorkItemTransition;
import org.kie.kogito.process.workitems.impl.DefaultKogitoWorkItemHandler;
import org.kie.kogito.quarkus.processes.devservices.DevModeWorkflowLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class DummyWorkItemHandler extends DefaultKogitoWorkItemHandler {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DevModeWorkflowLogger.class);

    private int errorsCount = 0;
    
    DummyRestClient dummyRestClient;

    public DummyWorkItemHandler(DummyRestClient dummyRestClient) {
        this.dummyRestClient = dummyRestClient;
    } 

    @Override
    public Optional<WorkItemTransition> activateWorkItemHandler(KogitoWorkItemManager manager, KogitoWorkItemHandler handler, KogitoWorkItem workitem, WorkItemTransition transition) {

        Response response = null;

        String errorStrategy = (String) workitem.getParameter("errorStrategy");
        Boolean throwException = (Boolean) workitem.getParameter("throwException");

        LOGGER.info("CALLING DUMMY WIH WITH ERRORSTRATEGY " + errorStrategy);

        /*
        try {
            dummyRestClient.getDummy();
        }
        catch(Exception ex) {
            handleError(errorStrategy, ex);
        }
        */
        
        if(throwException) {
            errorsCount = new Random().nextInt(5);
            if(errorsCount < 2) {
                if(errorStrategy.equals("RETRY")
                    || errorStrategy.equals("COMPLETE")
                    || errorStrategy.equals("RETHROW")) {
                    
                    handleError(errorStrategy, new WorkItemExecutionException(errorStrategy));

                } else if (errorStrategy.equals("ABORT")) {
                    return Optional.of(handler.abortTransition(workitem.getPhaseStatus()));
                } else {
                    // Don’t forget to finish the work item otherwise the process
                    // will be active infinitely and never will pass the flow
                    // to the next node.
                    return Optional.of(handler.completeTransition(workitem.getPhaseStatus(), new HashMap<>()));
                }
                System.out.println("ERRORS COUNT: " + errorsCount);
            }
        }

        LOGGER.info("WIH SUCCESSFULLY EXECUTED");
        return Optional.of(handler.completeTransition(workitem.getPhaseStatus(), new HashMap<>()));
    }

    @Override
    public Optional<WorkItemTransition> abortWorkItemHandler(KogitoWorkItemManager manager, KogitoWorkItemHandler handler, KogitoWorkItem workitem, WorkItemTransition transition) {
        LOGGER.debug("ABORT!");
        return Optional.empty();
    }

    private void handleError(String strategy, Throwable ex) {
        /*
        throw new ProcessWorkItemHandlerException("error_handling",
                ProcessWorkItemHandlerException.HandlingStrategy.valueOf(strategy),
                new IllegalStateException(strategy + " strategy test"),
                2);
         */
        throw new WorkItemHandlerRuntimeException(ex);
    }
}