package com.social.message.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.social.common.domain.BaseDomain;
import com.social.message.constant.MessageStatus;
import com.social.message.constant.MessageTypeStatus;
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
public class EditHistory extends BaseDomain {
    @Id
    private String id;
    private String tempId;
    private String conversationId;
    private String senderId;
    private String content;
    private MessageTypeStatus type;
    private MessageStatus status;
    private Instant sentAt;
    private List<Attachment> attachments;
    private Map<String, Long> summaryReaction;
    private List<Mention> mentions;
    private Reply replyTo;
    private boolean isEdited;
    private boolean isPinned;
    private String refId;
}
