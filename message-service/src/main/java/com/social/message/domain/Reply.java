package com.social.message.domain;

import com.social.message.constant.MessageType;
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
    private String content;
    private MessageType type;
    private List<Attachment> attachments;
}
