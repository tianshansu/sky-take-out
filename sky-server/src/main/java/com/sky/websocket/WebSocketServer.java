package com.sky.websocket;

import org.springframework.stereotype.Component;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket Service
 */
@Component
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    // Store session objects
    private static Map<String, Session> sessionMap = new HashMap<>();

    /**
     * Method called when a connection is successfully established
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        System.out.println("Client: " + sid + " has established a connection");
        sessionMap.put(sid, session);
    }

    /**
     * Method called when a message is received from the client
     *
     * @param message The message sent by the client
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("Received message from client " + sid + ": " + message);
    }

    /**
     * Method called when the connection is closed
     *
     * @param sid The client session ID
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("Connection closed: " + sid);
        sessionMap.remove(sid);
    }

    /**
     * Broadcast message to all clients
     *
     * @param message The message to be sent
     */
    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                // Server sends a message to the client
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
