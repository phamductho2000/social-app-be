package com.social.message.dto.request;

import lombok.Data;

@Data
public class SearchMessageRequestDto {
    private String conversationId;
    private int limit;
    private String searchAfter;
}
