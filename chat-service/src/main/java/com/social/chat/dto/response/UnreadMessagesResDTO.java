package com.social.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadMessagesResDTO {
    private String id;
    private String messageId;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private String mediaUrl;
    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
