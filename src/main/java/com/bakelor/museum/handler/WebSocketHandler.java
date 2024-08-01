package com.bakelor.museum.handler;

import com.bakelor.museum.dto.BeaconData;
import com.bakelor.museum.dto.BeaconProximity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException {
        sessions.add(session);
        System.out.println("WebSocket established");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws IOException {
        // Handle incoming messages here if needed
        System.out.println("Received message: " + message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws IOException {
        System.err.println("WebSocket transport error: " + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws IOException {
        sessions.remove(session);
        System.out.println("WebSocket connection closed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMessage(Object object) throws IOException {
        // Convert the BeaconData object to JSON and send it to all connected clients
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(object);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(jsonMessage));
            }
        }
    }

}
