package com.example.WaterDispenser.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class  WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  //Clients send messages to
        config.setApplicationDestinationPrefixes("/app");       //Clients recieves the msg from
    }
    @Override
    public void  registerStompEndpoints(StompEndpointRegistry registry){
        registry.addEndpoint("/ws/esp32")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
