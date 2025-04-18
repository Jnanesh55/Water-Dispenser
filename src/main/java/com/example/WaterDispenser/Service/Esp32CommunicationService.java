package com.example.WaterDispenser.Service;

import com.example.WaterDispenser.Controller.PaymentController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class Esp32CommunicationService {

     @Autowired
    private final SimpMessagingTemplate messagingTemplate;

    public Esp32CommunicationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

//    public void confirmActivation(String transactionId) {
//        messagingTemplate.convertAndSend("/topic/confirmation/" + transactionId, "activated");
//        System.out.println("activated esp");}

    public void sendActivationSignal(String path, String activate) {
        messagingTemplate.convertAndSend("/topic/motor/" , "activate:5000"); // 5000ms = 5 seconds
        System.out.println("signal sent");
    }
}

