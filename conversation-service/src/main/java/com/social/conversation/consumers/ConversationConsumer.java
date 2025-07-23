package com.social.conversation.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.common.exception.AppException;
import com.social.conversation.dto.request.MarkReadMessageReqDto;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.conversation.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
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

    @KafkaListener(topics = "SENT_MESSAGE", groupId = "conversation_sent_message")
    public void listenMessage(String payload) {
        MessageResDTO req;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            req = objectMapper.readValue(payload, MessageResDTO.class);
            List<UserConversationResDTO> result = userConversationService.handleNewMessage(req);
//            kafkaTemplate.send("UPDATED_CONVERSATION", objectMapper.writeValueAsString(result));
            result.forEach(r -> {
                ProducerRecord<String, String> record = null;
                try {
                    record = new ProducerRecord<>(
                            "UPDATED_CONVERSATION",
                            null,
                            r.getUserId(),
                            objectMapper.writeValueAsString(r)
                    );
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                kafkaTemplate.send(record);
            });

        } catch (JsonProcessingException | ChatServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "MARKED_READ_MESSAGE", groupId = "marked_read_message")
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
