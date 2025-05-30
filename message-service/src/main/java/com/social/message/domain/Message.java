package com.social.message.domain;

import com.social.common.domain.BaseDomain;
import com.social.message.constant.MessageStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Document(value = "message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseDomain {
    @Id
    private String id;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private MessageStatus status;
    private List<Attachment> attachments;
}
