package com.social.storage.dto.request;

import lombok.Builder;

@Builder
public record InitUploadChunkRequestDto(
    String fileName,
    String contentType,
    String filePath,
    long fileSize,
    String extension
) {

}
