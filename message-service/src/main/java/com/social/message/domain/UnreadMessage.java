package com.social.message.domain;

import com.social.common.domain.BaseDomain;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(value="unread_messages")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnreadMessage extends BaseDomain {
    @Id
    private String id;
    private ObjectId conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private String mediaUrl;
}
