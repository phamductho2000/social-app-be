package com.social.websocket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.dto.MessageDTO;
import com.social.websocket.service.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatWebSocketServiceImpl implements ChatWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(MessageDTO messageDTO) throws JsonProcessingException {
        if (Objects.nonNull(messageDTO)) {
            String messageId = new ObjectId().toString();
            messageDTO.setId(messageId);
            String payload = objectMapper.writeValueAsString(messageDTO);
//            messagingTemplate.convertAndSend("/topic/conversation" + messageDTO.getConversationId(), payload);
            kafkaTemplate.send("SAVE_MESSAGE", payload);
        }
    }
}
