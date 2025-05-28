package com.social.conversation.dto.request;

import lombok.Data;

@Data
public class SearchConversationRequestDto {
    private String searchValue;
    private String searchAfter;
    private int limit;
}
