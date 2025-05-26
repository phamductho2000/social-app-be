package com.social.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(value = "message_read_status")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageReadStatus {

    @Id
    private String id;
    private String userId;
    private ObjectId conversationId;
    private ObjectId messageId;
    private Instant readAt;
}
