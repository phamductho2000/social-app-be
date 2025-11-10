package com.social.message.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.Attachment;
import com.social.message.domain.Mention;
import com.social.message.domain.ReactionHistory;
import com.social.message.domain.Reply;
import com.social.message.parse.MongoDateDeserializer;
import com.social.message.parse.ObjectIdDeserializer;
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
public class MessagePayloadDto {
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    @JsonAlias("_id")
    private String id;
    private String tempId;
    private String conversationId;
    private String senderId;
    private String userName;
    private String content;
    private MessageType type;
    private MessageStatus status;
    @JsonDeserialize(using = MongoDateDeserializer.class)
    private Instant sentAt;
    private List<Attachment> attachments;
    private List<ReactionHistory> reactionHistories;
    private List<Mention> mentions;
    private Reply replyTo;
}
