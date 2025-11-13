package com.social.message.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.Attachment;
import com.social.message.domain.Mention;
import com.social.message.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResDTO {
    private String id;
    private Integer msgId;
    private String clientMsgId;
    private String conversationId;
    private String senderId;
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
