package com.social.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.websocket.dto.UserConversationResDTO;
import org.springframework.messaging.Message;

import java.util.List;

public interface ChatWebSocketService {
    void sendMessage(Message<Object> message) throws JsonProcessingException;

    void sendConversationChange(List<UserConversationResDTO> conversations);
}
