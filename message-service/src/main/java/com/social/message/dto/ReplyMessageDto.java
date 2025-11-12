package com.social.message.dto;

import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.Attachment;
import com.social.message.domain.Mention;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyMessageDto {
    private String clientMsgId;
    private String chatId;
    private String senderId;
    private String content;
    private MessageType type;
    private MessageStatus status;
    private List<Attachment> attachments;
    private List<Mention> mentions;
}
