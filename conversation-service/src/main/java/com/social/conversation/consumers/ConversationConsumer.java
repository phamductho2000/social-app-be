package com.social.conversation.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.conversation.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@RequiredArgsConstructor
public class ConversationConsumer {

    private final ObjectMapper objectMapper;

    private final UserConversationService userConversationService;

    @KafkaListener(topics = "NEW_MESSAGE_CONVERSATION", groupId = "chat-app")
    public void listen(String message) {
        MessageResDTO res;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            res = objectMapper.readValue(message, MessageResDTO.class);
            userConversationService.handleNewMessage(res);
        } catch (JsonProcessingException | ChatServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
