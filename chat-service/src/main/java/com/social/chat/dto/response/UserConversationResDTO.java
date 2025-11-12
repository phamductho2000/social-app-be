package com.social.chat.dto.response;

import com.social.chat.dto.request.MessageReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private MessageReqDTO lastMessage;
}
