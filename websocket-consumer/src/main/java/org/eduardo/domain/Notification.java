package org.eduardo.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Notification {

    private static Random randomGenerator = new Random();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime now = LocalDateTime.now();
    private static final String SYSTEM = "Consumer - Websocket";

    private String sender;
    private String content;
    private String timestamp;

    public Notification buildUserNotification() {
        setContent(String.format("User %s sent message to websocket: %s", sender, content));
        setSender(SYSTEM);
        setTimestamp(dtf.format(now));
        return this;
    }

    public static Notification buildAckNotification() {
        return Notification
                .builder()
                .sender(SYSTEM)
                .content("Connected to Consumer")
                .timestamp(dtf.format(now))
                .build();
    }

    public static Notification buildClosedConnectionNotification(String userName) {
        return Notification
                .builder()
                .sender(SYSTEM)
                .content(String.format("User %s closed connection", userName))
                .timestamp(dtf.format(now))
                .build();
    }
}
