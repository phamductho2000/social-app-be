package com.social.message.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionHistoryReqDto {
    private String id;
    private String userId;
    private String messageId;
    private String emoji;
    private String unified;
}
