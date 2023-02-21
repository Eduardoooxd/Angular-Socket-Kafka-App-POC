package org.eduardo;

import org.eduardo.domain.Notification;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.eduardo.WebSocketConverter.parseToJson;
import static org.eduardo.WebSocketConverter.parseToNotification;

@ApplicationScoped
public class WebSocketService {
    private static final Logger LOGGER = Logger.getLogger(WebSocketService.class.getCanonicalName());
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    protected void addUserConnection(Session userSession, String userName) {
        sessions.put(userName, userSession);
        LOGGER.log(Level.INFO, String.format("User %s connected", userName));

        Notification ackNotification = Notification.buildAckNotification();

        publishToUser(userSession, ackNotification);
    }

    protected void closeUserConnection(String userName) {
        sessions.remove(userName);
        LOGGER.log(Level.INFO, String.format("User %s closed connection", userName));

        Notification closedConnectionNotification = Notification.buildClosedConnectionNotification(userName);

        publishToAllUsers(closedConnectionNotification);
    }

    protected void errorOnUserConnection(String userName, Throwable throwable) {
        sessions.remove(userName);
        LOGGER.log(Level.SEVERE, String.format("User %s produced the following error %s", userName, throwable.getMessage()));
    }

    protected void onUserMessage(String json, String userName) {
        LOGGER.log(Level.INFO, String.format("User %s sent message %s", userName, json));

        Notification notification = parseToNotification(json);
        publishToAllUsers(notification);

        Notification systemNotification = notification.buildUserNotification();
        publishToAllUsers(systemNotification);
    }

    public void publishToUser(Session session, Object outgoingMessage) {
        String json = parseToJson(outgoingMessage);
        session
                .getAsyncRemote()
                .sendObject(json, sendResult -> {
                    if (!sendResult.isOK()) {
                        LOGGER.log(Level.SEVERE, "Error sending to user: " + sendResult.getException().getMessage());
                    }
                });
    }

    public void publishToAllUsers(Object outgoingMessage) {
        String json = parseToJson(outgoingMessage);
        sessions
                .values()
                .forEach(session -> session
                        .getAsyncRemote()
                        .sendObject(json, sendResult -> {
                            if (!sendResult.isOK()) {
                                LOGGER.log(Level.SEVERE, "Error sending to ALL users: " + sendResult.getException().getMessage());
                            }
                        })

                );
    }
}
