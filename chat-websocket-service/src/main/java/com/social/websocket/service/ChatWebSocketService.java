package com.social.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.websocket.dto.MessageDTO;
import org.springframework.messaging.Message;

public interface ChatWebSocketService {
    void sendMessage(Message<Object> message) throws JsonProcessingException;
}
