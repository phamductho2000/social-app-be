package com.social.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageReadStatusReqDTO {
    private String id;
    private String userId;
    private String conversationId;
    private String messageId;
    private Instant readAt;
}
