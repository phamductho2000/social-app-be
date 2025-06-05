package com.social.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
    private String id;
//    private String messageId;
    private String conversationId;
    private String senderId;
    private List<String> recipientIds;
    private String content;
    private String contentType;
    private String mediaUrl;
    private String type;
    private Boolean isRead;
    private List<MultipartFile> attachments;
    private String createdBy;
    private String updatedBy;
}
