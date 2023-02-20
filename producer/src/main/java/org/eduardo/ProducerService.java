package org.eduardo;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eduardo.domain.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class ProducerService {
    private static final Logger LOGGER = Logger.getLogger(ProducerService.class.getCanonicalName());

    private static final String KAFKA_OUTGOING_CHANNEL = "message-channel";

    @Inject
    @Channel(KAFKA_OUTGOING_CHANNEL)
    Emitter<Message> messageChannelEmitter;

    @Outgoing(KAFKA_OUTGOING_CHANNEL)
    public Multi<Message> sendRandomMessage() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(15))
                .map(x -> {
                    LOGGER.log(Level.INFO, "Started to broadcast random message");
                    return Message.randomMessage();
                });
    }

    public Message sendUserMessage(Message userMessage) {
        Message messageToBroadcast = userMessage.manipulateUserMessage();
        LOGGER.log(Level.INFO, "Started to broadcast user message");
        this.broadcastMessage(messageToBroadcast);
        return messageToBroadcast;
    }

    private void broadcastMessage(Message message) {
        this.messageChannelEmitter.send(message);
    }
}
