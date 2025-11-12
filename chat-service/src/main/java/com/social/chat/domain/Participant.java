package com.social.chat.domain;

import com.social.common.domain.BaseDomain;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Document(value = "participant")
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
