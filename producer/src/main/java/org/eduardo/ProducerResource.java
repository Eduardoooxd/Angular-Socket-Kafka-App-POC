package org.eduardo;

import org.eduardo.domain.Message;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/producer")
public class ProducerResource {

    @Inject
    ProducerService service;

    @POST
    public Response createMessage(Message message) {
        Message messageBroadcasted = service.sendUserMessage(message);

        return Response
                .status(CREATED)
                .entity(messageBroadcasted)
                .build();
    }
}