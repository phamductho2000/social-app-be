package com.social.websocket.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.dto.MessageResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_MESSAGE;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "SAVE_NEW_MESSAGE_SUCCESS", groupId = "chat-app")
    public void listenMessage(String message) {
        MessageResDTO res;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            res = objectMapper.readValue(message, MessageResDTO.class);
            messagingTemplate.convertAndSend(TOPIC_LISTEN_MESSAGE + res.getConversationId(), res);
//            kafkaTemplate.send("NEW_MESSAGE_CONVERSATION", message);
        } catch (JsonProcessingException e) {
//            kafkaTemplate.send("SAVE_MESSAGE_FAILED", message);
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "UPDATE_CONVERSATION_SUCCESS", groupId = "chat-app")
    public void listenConversation(String message) {
        MessageResDTO res;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            res = objectMapper.readValue(message, MessageResDTO.class);
            messagingTemplate.convertAndSend(TOPIC_LISTEN_MESSAGE + res.getConversationId(), res);
            kafkaTemplate.send("NEW_MESSAGE_CONVERSATION", message);
        } catch (JsonProcessingException e) {
//            kafkaTemplate.send("SAVE_MESSAGE_FAILED", message);
            throw new RuntimeException(e);
        }
    }
}
