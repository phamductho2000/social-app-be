package com.social.message.dto;

import com.social.message.constant.MessageType;
import com.social.message.domain.embedded.Attachment;
import com.social.message.domain.embedded.Content;
import com.social.message.domain.embedded.Mention;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditMessageDto {
    private Integer msgId;
    private String conversationId;
    private Content content;
    private MessageType type;
    private List<Attachment> attachments;
    private List<Mention> mentions;
}
