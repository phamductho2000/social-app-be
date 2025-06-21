package com.social.message.cdc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.model.changestream.FullDocument;
import com.social.message.dto.response.MessagePayloadDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageCdc {

    private final MongoTemplate mongoTemplate;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @PostConstruct
    public void startChangeStreamListener() {
        watchMessageCollection();
    }

    private void watchMessageCollection() {
        CompletableFuture.runAsync(() -> {
            try {
                mongoTemplate.getCollection("message")
                        .watch()
                        .fullDocument(FullDocument.UPDATE_LOOKUP)
                        .forEach(change -> {
                            Document fullDoc = change.getFullDocument();
                            if (Objects.nonNull(fullDoc)) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                objectMapper.registerModule(new JavaTimeModule());
                                try {
                                    MessagePayloadDto payload = objectMapper.readValue(fullDoc.toJson(), MessagePayloadDto.class);
                                    kafkaTemplate.send("SAVE_NEW_MESSAGE_SUCCESS", objectMapper.writeValueAsString(payload));
                                    log.info("Message saved to mongo");
                                } catch (JsonProcessingException e) {
//                                    throw new RuntimeException(e);
                                    log.error(e.getMessage());
                                }
                            }
                        });

            } catch (Exception e) {
//                log.error("Error in change stream listener", e);
            }
        });
    }
}
