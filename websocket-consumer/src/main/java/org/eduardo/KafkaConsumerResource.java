package org.eduardo;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eduardo.domain.Message;

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
    public void consume(Message message){
        LOGGER.log(Level.INFO,String.format("Record received with Message: %s", message));
        webSocketService.publishToAllUsers(message);
    }
}
