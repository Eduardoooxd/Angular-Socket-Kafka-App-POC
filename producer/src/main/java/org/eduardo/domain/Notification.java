package org.eduardo.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Data
@Builder
public class Notification {
    private static Random randomGenerator = new Random();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final String SYSTEM = "Producer";

    private String sender;
    private String content;
    private String timestamp;

    public static Notification buildRandomNotification() {
        double randomNumber = randomGenerator.nextDouble() * 1000;
        LocalDateTime now = LocalDateTime.now();

        return Notification
                .builder()
                .sender(SYSTEM)
                .content(String.format("Producer emitted random number %.2f", randomNumber))
                .timestamp(dtf.format(now))
                .build();
    }

    public Notification buildUserSentNotification() {
        LocalDateTime now = LocalDateTime.now();
        setContent(String.format("Content of user %s sent by HTTP: %s", sender ,content));
        setSender(SYSTEM);
        setTimestamp(dtf.format(now));
        return this;
    }
}
