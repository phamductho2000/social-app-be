package com.social.websocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.websocket.dto.MessageDTO;

public interface ChatWebSocketService {
    void sendMessage(MessageDTO messageDTO) throws JsonProcessingException;
}
