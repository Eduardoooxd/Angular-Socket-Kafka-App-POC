package org.eduardo;

import org.eduardo.domain.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eduardo.WebSocketConverter.parseToJson;
import static org.eduardo.WebSocketConverter.parseToMessage;

@ApplicationScoped
public class WebSocketService {
    private static final Logger LOGGER = Logger.getLogger(WebSocketService.class.getCanonicalName());
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    protected void addUserConnection(Session userSession, String userName) {
        sessions.put(userName, userSession);
        LOGGER.log(Level.INFO, String.format("User %s connected", userName));

        Message ackMessage = Message.createAckMessage();

        publishToUser(userSession, ackMessage);
    }

    protected void closeUserConnection(String userName) {
        sessions.remove(userName);
        LOGGER.log(Level.INFO, String.format("User %s closed connection", userName));

        Message closedConnectionMessage = Message.createClosedConnectionMessage(userName);

        publishToAllUsers(closedConnectionMessage);
    }

    protected void errorOnUserConnection(String userName, Throwable throwable) {
        sessions.remove(userName);
        LOGGER.log(Level.SEVERE, String.format("User %s produced the following error %s", userName, throwable.getMessage()));
    }

    protected void onUserMessage(String json, String userName) {
        LOGGER.log(Level.INFO, String.format("User %s sent message %s", userName, json));
        Message message = parseToMessage(json);
        message = message.manipulateUserMessage();
        publishToAllUsers(message);
        // publish to everyone
    }

    public void publishToUser(Session session, Object messageToUser) {
        String json = parseToJson(messageToUser);
        session
                .getAsyncRemote()
                .sendObject(json, sendResult -> {
                    if (!sendResult.isOK()) {
                        LOGGER.log(Level.SEVERE, "Error sending message to user: " + sendResult.getException().getMessage());
                    }
                });
    }

    public void publishToAllUsers(Object messageToUser) {
        String json = parseToJson(messageToUser);
        sessions
                .values()
                .forEach(session -> session
                        .getAsyncRemote()
                        .sendObject(json, sendResult -> {
                            if (!sendResult.isOK()) {
                                LOGGER.log(Level.SEVERE, "Error sending message to ALL users: " + sendResult.getException().getMessage());
                            }
                        })

                );
    }
}
