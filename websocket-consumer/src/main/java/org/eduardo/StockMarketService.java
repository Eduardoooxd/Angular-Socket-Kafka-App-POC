package org.eduardo;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eduardo.domain.Message;

import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Level;
import java.util.logging.Logger;

@ApplicationScoped
public class StockMarketService {
    private static final Logger LOGGER = Logger.getLogger(StockMarketService.class.getName());

    @Incoming("stockmarket-update")
    public void consume(Message message){
        LOGGER.log(Level.WARNING,String.format("Record received with Message: %s", message.toString()));
    }
}
