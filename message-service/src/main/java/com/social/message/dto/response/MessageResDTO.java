package com.social.message.dto.response;

import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.Attachment;
import com.social.message.domain.Mention;
import com.social.message.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResDTO {
    private String id;
    private String tempId;
    private String conversationId;
    private String senderId;
    private String userName;
    private String content;
    private MessageType type;
    private MessageStatus status;
    private Instant sentAt;
    private List<Attachment> attachments;
    private Map<String, Long> summaryReaction;
    private List<Mention> mentions;
    private Reply replyTo;
    private boolean isEdited;
    private boolean isPinned;
}
