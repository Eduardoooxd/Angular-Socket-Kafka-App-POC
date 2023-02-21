package org.eduardo;

import org.eduardo.domain.Notification;

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
    public Response createNotification(Notification notification) {
        Notification notificationBroadcast = service.createUserNotification(notification);

        return Response
                .status(CREATED)
                .entity(notificationBroadcast)
                .build();
    }
}