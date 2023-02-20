package org.eduardo.domain;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String sender;
    private String content;
    private String timestamp;
}
