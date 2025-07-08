package com.social.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.messaging.Message;

import java.util.List;

public interface ChatWebSocketService {
    void sendMessage(String message) throws JsonProcessingException;

    void reactMessage(String payload);
}
