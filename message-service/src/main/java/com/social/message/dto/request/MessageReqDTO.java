package com.social.message.dto.request;

import com.social.message.domain.Reply;
import com.social.message.dto.response.AttachmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReqDTO {
    private String id;
    private String conversationId;
    private String senderId;
    private String senderName;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
    private String username;
    private Reply reply;
}
