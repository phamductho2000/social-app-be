package com.social.message.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.Instant;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record ReactionReqDto(
    String id,
    String conversationId,
    String messageId,
    String userId,
    String emoji,
    String unified,
    Instant createdAt,
    Instant updatedAt
) {

}