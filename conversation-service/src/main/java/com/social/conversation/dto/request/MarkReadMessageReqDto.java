package com.social.conversation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MarkReadMessageReqDto {
    private String userId;
    private String conversationId;
    private int size;
}
