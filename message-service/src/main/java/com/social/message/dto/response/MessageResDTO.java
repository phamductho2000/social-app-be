package com.social.message.dto.response;

import com.social.message.constant.MessageStatus;
import com.social.message.domain.Reaction;
import com.social.message.domain.Reply;
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
    private String senderName;
    private MessageStatus status;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
    private List<Reaction> reactions;
    private Reply reply;
    private Instant createdAt;
    private Instant updatedAt;
}
