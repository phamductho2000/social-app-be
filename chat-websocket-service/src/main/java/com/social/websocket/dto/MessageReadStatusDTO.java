package com.social.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageReadStatusDTO {
    private String id;
    private String userId;
    private String conversationId;
    private String messageId;
    private Instant readAt;
}
