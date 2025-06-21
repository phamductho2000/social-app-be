package com.social.websocket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.social.websocket.constant.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResDTO {
    private String id;
    private String conversationId;
    private String senderId;
    private String senderName;
    private MessageStatus status;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
    private List<ReactionDto> reactions;
    private ReplyDto reply;
    private Instant createdAt;
}
