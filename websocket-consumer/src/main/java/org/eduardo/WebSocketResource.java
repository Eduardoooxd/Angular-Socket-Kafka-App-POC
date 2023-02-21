package org.eduardo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket/{name}")
@ApplicationScoped
public class WebSocketResource {
    @Inject
    WebSocketService webSocketService;

    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        webSocketService.addUserConnection(session, name);
    }

    @OnClose
    public void onClose(@PathParam("name") String name) {
        webSocketService.closeUserConnection(name);
    }

    @OnError
    public void onError(@PathParam("name") String name, Throwable throwable) {
        webSocketService.errorOnUserConnection(name, throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("name") String name) {
        webSocketService.onUserMessage(message, name);
    }
}
