package com.social.storage.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record UploadChunkResponseDto(
    String uploadId,
    int totalChunks,
    long chunkSize,
    List<ChunkUrl> chunkUrls
) {
  public record ChunkUrl(int partNumber, String url) {

  }
}
