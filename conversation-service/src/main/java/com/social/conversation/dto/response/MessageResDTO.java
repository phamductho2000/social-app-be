package com.social.conversation.dto.response;

import com.social.conversation.constants.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResDTO {
    private String id;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
    private MessageStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
