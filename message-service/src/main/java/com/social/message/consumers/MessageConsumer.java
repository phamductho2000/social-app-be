package com.social.message.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final MessageService messageService;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "SAVE_NEW_MESSAGE", groupId = "chat-app")
    public void listen(String message) {
        MessageReqDTO req;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            req = objectMapper.readValue(message, MessageReqDTO.class);
            MessageResDTO res = messageService.save(req);
//            if (Objects.nonNull(res)) {
//                String payload = objectMapper.writeValueAsString(res);
//                kafkaTemplate.send("SAVE_NEW_MESSAGE_SUCCESS", payload);
//            }
        } catch (JsonProcessingException | ChatServiceException e) {
            kafkaTemplate.send("SAVE_NEW_MESSAGE_FAILED", message);
            throw new RuntimeException(e);
        }
    }
}
