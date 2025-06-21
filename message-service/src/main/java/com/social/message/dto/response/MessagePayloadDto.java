package com.social.message.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.social.message.constant.MessageStatus;
import com.social.message.domain.Reaction;
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
public class MessagePayloadDto {
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    @JsonAlias("_id")
    private String id;
    private String conversationId;
    private String senderId;
    private String senderName;
    private MessageStatus status;
    private String content;
    private String contentType;
    private List<AttachmentDTO> attachments;
    private List<Reaction> reactions;
    private Reply reply;
    @JsonDeserialize(using = MongoDateDeserializer.class)
    private Instant createdAt;
}
