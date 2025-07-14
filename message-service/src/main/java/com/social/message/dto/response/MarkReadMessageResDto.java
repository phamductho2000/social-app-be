package com.social.message.dto.response;

import com.social.message.constant.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkReadMessageResDto {
    private String id;
    private String conversationId;
    private MessageStatus status;
}
