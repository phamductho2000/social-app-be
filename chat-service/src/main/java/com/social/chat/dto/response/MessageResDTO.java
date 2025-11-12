package com.social.chat.dto.response;

import com.social.chat.constants.MessageStatus;
import com.social.chat.constants.MessageTypeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
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
    private MessageTypeStatus type;
    private MessageStatus status;
    private Instant sentAt;
    //    private List<Attachment> attachments;
    private Map<String, Long> summaryReaction;
//    private List<Mention> mentions;
//    private Reply replyTo;
}
