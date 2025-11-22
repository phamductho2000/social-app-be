package com.social.message.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.social.common.domain.BaseDomain;
import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageType;
import com.social.message.domain.embedded.Attachment;
import com.social.message.domain.embedded.Content;
import com.social.message.domain.embedded.Mention;
import com.social.message.domain.embedded.Reply;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(value = "message_history")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageHistory extends BaseDomain {
    @Id
    private String id;
    private Integer msgId;
    private String clientMsgId;
    private String conversationId;
    private String senderId;
    private Content content;
    private MessageType type;
    private MessageStatus status;
    private Map<String, Long> summaryReaction;
    private List<Mention> mentions;
    private Reply reply;
    private boolean isEdited;
    private boolean isPinned;
    private String groupId;
    private Instant sentAt;
}
