package org.eduardo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eduardo.domain.Notification;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WebSocketConverter {

    private static final Logger LOGGER = Logger.getLogger(WebSocketConverter.class.getCanonicalName());

    public static String parseToJson(Object objectToParse) {
        // Parsing manually to JSON, but this ideally should be automatically serialize by Jackson, but it was throwing errors

        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.writeValueAsString(objectToParse);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Error parsing object %s", objectToParse.toString());

            return "";
        }
    }

    public static Notification parseToNotification(String jsonString) {
        // Parsing manually to Notification, but this ideally should be automatically deserialize by Jackson, but it was throwing errors
        try {
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(jsonString, Notification.class);
        } catch (JsonProcessingException e) {
            LOGGER.log(Level.SEVERE, "Error parsing JSON: %s", jsonString);

            return Notification
                    .builder()
                    .build();
        }
    }
}
