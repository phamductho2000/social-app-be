package com.social.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantReqDTO {
    private String id;
    private String conversationId;
    private String userId;
    private String fullName;
    private String avatar;
    private String role;
    private String status;
    private String invitedBy;
    private Instant joinedAt;
    private Instant leftAt;
}
