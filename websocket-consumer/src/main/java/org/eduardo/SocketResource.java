package org.eduardo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import io.quarkus.scheduler.Scheduled;
import org.apache.commons.lang3.RandomStringUtils;
import org.eduardo.domain.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/websocket/{name}")
@ApplicationScoped
public class SocketResource {
    private static final Logger LOGGER = Logger.getLogger(SocketResource.class.getCanonicalName());

    private static final ObjectWriter jsonWritter = JsonMapper
            .builder()
            .findAndAddModules()
            .build()
            .writer()
            .withDefaultPrettyPrinter();
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    // Adicionar array de mensagens

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) throws JsonProcessingException {
        sessions.put(name, session);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        Message ackMessage = Message
                .builder()
                .sender("System")
                .content("Connected to Consumer")
                .timestamp(dtf.format(now))
                .build();

        ObjectWriter writter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writter.writeValueAsString(ackMessage);

        session.getAsyncRemote().sendObject(json);
    }

    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        sessions.remove(name);

        publish("User " + name + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        sessions.remove(name);
        LOGGER.log(Level.WARNING, "Fatal Error on socket with error: " + throwable.getMessage());
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("name") String name) throws JsonProcessingException {
        LOGGER.log(Level.INFO, String.format("User with name %s, sent message %s", name, message));

        publish(message);
    }

    @Scheduled(every = "10s")
    public void streamVeryImportantMessage() throws JsonProcessingException {
        Message message = this.generateMessage();
        ObjectWriter writter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = writter.writeValueAsString(message);

        publish(json);
    }

    private void publish(Object object) {
        LOGGER.log(Level.INFO, "New Message Published");
        sessions
                .values()
                .forEach(session -> session
                        .getAsyncRemote().sendObject(object, result -> {
                            if (!result.isOK()) {
                                LOGGER.log(Level.WARNING, "Error sending message: " + result.getException());
                            }
                        }));
    }

    private Message generateMessage() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        String randomString = RandomStringUtils.randomAlphabetic(10);

        return Message
                .builder()
                .sender("System")
                .content(randomString)
                .timestamp(dtf.format(now))
                .build();
    }

}
