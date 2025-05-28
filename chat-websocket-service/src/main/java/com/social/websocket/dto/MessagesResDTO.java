package com.social.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessagesResDTO {
    private String messageId;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private String mediaUrl;
    private Boolean isRead;
    private Instant createdAt;
    private Instant updatedAt;
}
