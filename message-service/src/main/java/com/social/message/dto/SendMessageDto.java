package com.social.message.dto;

import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.embedded.Attachment;
import com.social.message.domain.embedded.Mention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendMessageDto {
    private String clientMsgId;
    private String conversationId;
    private String senderId;
    private String content;
    private MessageType type;
    private MessageStatus status;
    private List<Attachment> attachments;
    private List<Mention> mentions;
}
