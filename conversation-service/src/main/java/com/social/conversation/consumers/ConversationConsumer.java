package com.social.conversation.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.common.exception.AppException;
import com.social.conversation.constants.MessageStatus;
import com.social.conversation.dto.request.MarkReadMessageReqDto;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.conversation.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@RequiredArgsConstructor
public class ConversationConsumer {

    private final ObjectMapper objectMapper;

    private final UserConversationService userConversationService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "SAVE_NEW_MESSAGE_SUCCESS", groupId = "chat-app-2")
    public void listenMessage(String payload) {
        MessageResDTO req;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            req = objectMapper.readValue(payload, MessageResDTO.class);
            if (req.getStatus() == MessageStatus.SENT) {
                List<UserConversationResDTO> result = userConversationService.handleNewMessage(req);
            }
//            kafkaTemplate.send("UPDATE_CONVERSATION_SUCCESS", objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException | ChatServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "MARK_READ_MESSAGE_SUCCESS", groupId = "chat-app-8")
    public void listenMark(String payload) {
        MarkReadMessageReqDto req;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            req = objectMapper.readValue(payload, MarkReadMessageReqDto.class);
            userConversationService.updateMarkRead(req);
        } catch (JsonProcessingException | AppException e) {
            throw new RuntimeException(e);
        }
    }
}
