package com.social.storage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadRequestDto {
  private String fileName;
  private String contentType;
  private byte[] fileData;
  private FileMetadata fileMetadata;

  @Data
  @Builder
  public static class FileMetadata {
    private String fileName;
    private String fileSize;
    private String extension;
  }
}
