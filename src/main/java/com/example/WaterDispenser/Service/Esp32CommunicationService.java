package com.example.WaterDispenser.Service;

import com.example.WaterDispenser.Websocket.Esp32WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class Esp32CommunicationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final Esp32WebSocketHandler esp32Handler;

    @Autowired
    public Esp32CommunicationService(SimpMessagingTemplate messagingTemplate,
                                     Esp32WebSocketHandler esp32Handler) {
        this.messagingTemplate = messagingTemplate;
        this.esp32Handler = esp32Handler;
    }

    public void sendActivationSignal(String path, String command) {
        // Send via STOMP
        messagingTemplate.convertAndSend("/topic/motor", "ACTIVATE:5000");
        messagingTemplate.convertAndSend("/ws/esp32/raw", "ACTIVATE:5000");
        // Also send via raw WebSocket
        esp32Handler.sendToEsp32("ACTIVATE:5000");

        System.out.println("Signal sent through both channels");
    }

    public void sendCommand(String command) {
        messagingTemplate.convertAndSend("/topic/motor", command);
        esp32Handler.sendToEsp32(command);
        System.out.println("Sent to ESP32: " + command);
    }
}