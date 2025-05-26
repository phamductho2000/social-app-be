package com.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.dto.MessageDTO;

public interface ChatWebSocketService {
    void sendMessage(MessageDTO messageDTO) throws JsonProcessingException;
}
