package org.eduardo.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Data
@Builder
public class Message {
    private static Random randomGenerator = new Random();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime now = LocalDateTime.now();

    private String sender;
    private String content;
    private String timestamp;

    public static Message randomMessage() {
        double randomNumber = randomGenerator.nextDouble() * 1000;

        return Message
                .builder()
                .sender("Kafka Producer")
                .content(String.format("Producer emitted random number %.2f", randomNumber))
                .timestamp(dtf.format(now))
                .build();
    }

    public Message manipulateUserMessage() {
        setContent(String.format("Content of user sent from Producer: %s", content));
        setTimestamp(dtf.format(now));
        return this;
    }
}
