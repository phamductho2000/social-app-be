package com.social.conversation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConversationReqDTO {
    private String id;
    private String userId;
    private String type;
    private String name;
    private String avatar;
    private String conversationId;
    private Integer unreadCount;
    private Boolean notifications;
    private Boolean isPinned;
    private RecipientInfoDTO recipientInfo;
}
