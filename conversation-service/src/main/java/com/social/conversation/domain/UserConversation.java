package com.social.conversation.domain;

import com.social.common.domain.BaseDomain;
import com.social.conversation.constants.ConversationType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(value = "user_conversation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConversation extends BaseDomain {
    @Id
    private String id;
    private String userId;
    private String username;
    private String name;
    private String avatar;
    private ConversationType type;
    private String conversationId;
    private Integer unreadCount;
    private Boolean notifications;
    private Boolean isPinned;
    private Message lastMessage;
}
