package com.social.message.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reply {
    private String messageId;
    private String senderId;
    private String senderName;
    private String content;
    private String contentType;
    private List<Attachment> attachments;
}
