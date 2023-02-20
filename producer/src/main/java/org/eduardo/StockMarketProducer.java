package org.eduardo;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.eduardo.domain.StockNotification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Random;

@ApplicationScoped
public class StockMarketProducer {

    private final Random random = new Random();

    @Inject
    @Channel("stockmarket-update")
    Emitter<StockNotification> stockNotificationEmitter;

    @Outgoing("stockmarket-update-1")
    public Multi<StockNotification> produce() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(5))
                .map(x -> random.nextDouble())
                .map(this::createNotification);
    }

    private StockNotification createNotification(Double randomPrice) {
        return new StockNotification("New Notification", randomPrice * 100000);
    }
}
