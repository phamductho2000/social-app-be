package com.social.storage.dto.redis;

import lombok.Builder;

@Builder
public record MultipartPartDto(
    int partNumber,
    String eTag
) {

}
