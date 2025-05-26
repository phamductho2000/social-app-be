package com.social.conversation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResDTO {
    private String id;
    private String conversationId;
    private String fullName;
    private String userId;
    private String role;
    private String status;
    private String invitedBy;
    private Instant joinedAt;
    private Instant leftAt;
}
