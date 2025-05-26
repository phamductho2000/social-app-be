package com.social.dto.request;

import lombok.Data;

@Data
public class SearchUserRequestDto {
    private String searchValue;
    private String searchAfter;
    private int limit;
}
