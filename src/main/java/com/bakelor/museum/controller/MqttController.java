package com.bakelor.museum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class MqttController {

    private CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/mqtt-stream")
    public SseEmitter stream() {
        System.out.println("New SSE connection established");
        SseEmitter emitter = new SseEmitter(1800000L); // 30 dakika timeout
        emitters.add(emitter);
        emitter.onCompletion(() -> {
            System.out.println("SSE connection completed");
            emitters.remove(emitter);
        });
        emitter.onTimeout(() -> {
            System.out.println("SSE connection timed out");
            emitters.remove(emitter);
        });
        return emitter;
    }

    public void sendToClients(String message) {
        System.out.println("Received MQTT message: " + message);
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                System.err.println("Error sending message to client: " + e.getMessage());
                emitters.remove(emitter);
            }
        }
    }
}
