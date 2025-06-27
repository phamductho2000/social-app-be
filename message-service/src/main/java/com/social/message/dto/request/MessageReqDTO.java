package com.social.message.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageTypeStatus;
import com.social.message.domain.Attachment;
import com.social.message.domain.Mention;
import com.social.message.domain.Reaction;
import com.social.message.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private MessageTypeStatus type;
    private MessageStatus status;
    private List<Attachment> attachments;
    private List<Reaction> reactions;
    private List<Mention> mentions;
    private Reply replyTo;
}
