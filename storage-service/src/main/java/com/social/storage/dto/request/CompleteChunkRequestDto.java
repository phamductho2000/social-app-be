package com.social.storage.dto.request;

import java.util.List;
import lombok.Builder;

@Builder
public record CompleteChunkRequestDto(String uploadId, List<ChunkPart> parts) {

  public record ChunkPart(int partNumber, String etag) {

  }
}
