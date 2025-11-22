package com.social.message.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.embedded.Attachment;
import com.social.message.domain.embedded.Mention;
import com.social.message.domain.ReactionHistory;
import com.social.message.domain.embedded.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageReqDTO {
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
    private ReactionHistory reactionHistory;
    private List<Mention> mentions;
    private Reply replyTo;
}
