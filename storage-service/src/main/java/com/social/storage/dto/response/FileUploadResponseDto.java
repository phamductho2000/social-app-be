package com.social.storage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileUploadResponseDto {
  private String fileName;
  private String contentType;
  private String filePath;
  private long fileSize;
  private String extension;
}
