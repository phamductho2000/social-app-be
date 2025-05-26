package com.social.conversation.domain;

import com.social.domain.BaseDomain;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(value = "user_conversations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserConversation extends BaseDomain {
    @Id
    private String id;
    private String userId;
    private String name;
    private String avatar;
    private String type;
    private String conversationId;
    private Integer unreadCount;
    private Boolean notifications;
    private Boolean isPinned;
    private Message lastMessage;
}
