package com.social.websocket.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplyDto {
    private String messageId;
    private String senderId;
    private String senderName;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
}
