package com.social.chat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnreadMessagesReqDTO {
    private String id;
    private String messageId;
    private String conversationId;
    private String senderId;
    private String content;
    private String contentType;
    private String mediaUrl;
}
