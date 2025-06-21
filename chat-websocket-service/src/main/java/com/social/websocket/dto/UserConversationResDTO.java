package com.social.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConversationResDTO {
    private String id;
    private String userId;
    private String username;
    private String name;
    private String avatar;
    private String type;
    private String conversationId;
    private Integer unreadCount;
    private Boolean notifications;
    private Boolean isPinned;
    private MessageResDTO lastMessage;
    private Instant createdAt;
}
