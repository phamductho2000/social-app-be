package com.social.conversation.cdc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.model.changestream.FullDocument;
import com.social.conversation.dto.response.UserConversationPayloadDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class UserConversationCdc {

    private final MongoTemplate mongoTemplate;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void startChangeStreamListener() {
        watchCollection();
    }

    private void watchCollection() {
        CompletableFuture.runAsync(() -> {
            try {
                mongoTemplate.getCollection("user_conversation")
                        .watch()
                        .fullDocument(FullDocument.UPDATE_LOOKUP)
                        .forEach(change -> {
                            Document fullDoc = change.getFullDocument();
                            if (Objects.nonNull(fullDoc)) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                objectMapper.registerModule(new JavaTimeModule());
                                try {
                                    UserConversationPayloadDTO payload = objectMapper.readValue(fullDoc.toJson(), UserConversationPayloadDTO.class);
                                    kafkaTemplate.send("UPDATE_CONVERSATION_SUCCESS", objectMapper.writeValueAsString(payload));
                                } catch (JsonProcessingException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

            } catch (Exception e) {
//                log.error("Error in change stream listener", e);
            }
        });
    }
}
