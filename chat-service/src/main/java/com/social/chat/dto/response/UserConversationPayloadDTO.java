package com.social.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.social.chat.parse.MongoDateDeserializer;
import com.social.chat.parse.ObjectIdDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserConversationPayloadDTO {
    @JsonDeserialize(using = ObjectIdDeserializer.class)
    @JsonAlias("_id")
    private String id;
    private String userId;
    private String username;
    private String name;
    private String avatar;
    private String type;
    private String conversationId;
    private Integer unreadCount;
    private Boolean notifications;
    private Boolean isPinned;
    private Message lastMessage;
    @JsonDeserialize(using = MongoDateDeserializer.class)
    private Instant createdAt;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        @JsonDeserialize(using = ObjectIdDeserializer.class)
        @JsonAlias("_id")
        private String id;
        private String conversationId;
        private String senderId;
        private String content;
        private String contentType;
        private List<AttachmentDTO> attachments;
        @JsonDeserialize(using = MongoDateDeserializer.class)
        private Instant createdAt;
    }
}
