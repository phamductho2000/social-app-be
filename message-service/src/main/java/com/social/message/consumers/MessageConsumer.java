package com.social.message.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.ReactionReqDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.service.MessageHistoryService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final MessageHistoryService messageHistoryService;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "SENDING_MESSAGE", groupId = "message_service_sending_message")
    public void listenSending(String payload) {
        MessageReqDTO req;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            req = objectMapper.readValue(payload, MessageReqDTO.class);
            MessageResDTO res = messageHistoryService.save(req);

            ProducerRecord<String, String> record = new ProducerRecord<>(
                    "SENT_MESSAGE",
                    null,
                    res.getConversationId(),
                    objectMapper.writeValueAsString(res)
            );
            kafkaTemplate.send(record);

        } catch (JsonProcessingException | ChatServiceException e) {
            kafkaTemplate.send("SENDING_MESSAGE_FAILED", payload);
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "REACT_MESSAGE", groupId = "message_service_react_message")
    public void listenUpdate(String payload) {
        ReactionReqDto req;
        try {
            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
            req = objectMapper.readValue(payload, ReactionReqDto.class);
            MessageResDTO res = messageHistoryService.react(req);

            ProducerRecord<String, String> record = new ProducerRecord<>(
                "SENT_MESSAGE",
                null,
                res.getConversationId(),
                objectMapper.writeValueAsString(res)
            );
            kafkaTemplate.send(record);

        } catch (JsonProcessingException | ChatServiceException e) {
            kafkaTemplate.send("REACT_MESSAGE_FAILED", payload);
            throw new RuntimeException(e);
        }
    }
}
