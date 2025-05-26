package com.social.conversation.domain;

import com.social.domain.BaseDomain;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Document(value = "participants")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Participant extends BaseDomain {
    @Id
    private String id;
    private ObjectId conversationId;
    private String userId;
    private String role;
    private String status;
    private String invitedBy;
    private Instant joinedAt;
    private Instant leftAt;
}
