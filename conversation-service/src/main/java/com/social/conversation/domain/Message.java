package com.social.conversation.domain;

import com.social.conversation.dto.response.AttachmentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String id;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
    private Instant createdAt;
}
