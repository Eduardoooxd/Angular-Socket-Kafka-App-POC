package org.eduardo;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eduardo.domain.Notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class KafkaConsumerResource {
    private static final Logger LOGGER = Logger.getLogger(KafkaConsumerResource.class.getName());

    @Inject
    WebSocketService webSocketService;

    @Incoming("message-channel")
    public void consume(Notification notification){
        LOGGER.log(Level.INFO,String.format("Record received: %s", notification));
        webSocketService.publishToAllUsers(notification);
    }
}
