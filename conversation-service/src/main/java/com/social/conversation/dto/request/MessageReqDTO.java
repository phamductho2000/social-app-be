package com.social.conversation.dto.request;

import com.social.conversation.dto.response.AttachmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageReqDTO {
    private String id;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
}
