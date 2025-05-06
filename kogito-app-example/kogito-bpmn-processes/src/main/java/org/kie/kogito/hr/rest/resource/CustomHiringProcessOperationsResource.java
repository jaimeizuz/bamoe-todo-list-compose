package org.kie.kogito.hr.rest.resource;

import org.jboss.logging.Logger;
import org.kie.kogito.incubation.application.AppRoot;
import org.kie.kogito.incubation.common.EmptyDataContext;
import org.kie.kogito.incubation.processes.services.StatefulProcessService;
import org.kie.kogito.incubation.processes.services.contexts.ProcessMetaDataContext;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessService;
import org.kie.kogito.incubation.processes.LocalProcessId;
import org.kie.kogito.incubation.processes.ProcessInstanceId;

import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hiring")
public class CustomHiringProcessOperationsResource {

    Logger logger = Logger.getLogger(CustomHiringProcessOperationsResource.class);

    @Inject
    AppRoot appRoot;

    @Inject
    StatefulProcessService statefulProcessService;

    //@Inject
    //ProcessService processService;

    //@Inject()
    //@Named("hiring")
    //Process process;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/instances/{processInstanceId}/signals/{signalId}")
    public Response signalHiringProcessInstance(
        @Valid @NotNull @NotBlank @PathParam("processInstanceId") String processInstanceId, 
        @Valid @NotNull @NotBlank @PathParam("signalId") String signalId) throws Throwable {

        var pid = statefulProcessService.get(new ProcessInstanceId(new LocalProcessId("hiring"), processInstanceId)).meta()
                .as(ProcessMetaDataContext.class)
                .id(ProcessInstanceId.class);

        statefulProcessService.signal(pid.signals().get(signalId), EmptyDataContext.Instance);
        //processService.signalProcessInstance(process, processInstanceId, null, signalId).orElseThrow(() -> new NotFoundException());

        return Response.ok().build();
    }
}
