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
public class Message {

    private static Random randomGenerator = new Random();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static LocalDateTime now = LocalDateTime.now();

    private String sender;
    private String content;
    private String timestamp;

    public Message manipulateUserMessage() {
        setContent(String.format("User %s sent message to websocket: %s",sender, content));
        setTimestamp(dtf.format(now));
        return this;
    }

    public static Message createAckMessage() {
        return Message
                .builder()
                .sender("System")
                .content("Connected to Consumer")
                .timestamp(dtf.format(now))
                .build();
    }

    public static Message createClosedConnectionMessage(String userName) {
        return Message
                .builder()
                .sender("System")
                .content(String.format("User %s closed connection", userName))
                .timestamp(dtf.format(now))
                .build();
    }

}
