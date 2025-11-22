package com.social.storage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadChunkRequestDto {

  private String uploadId;
  private int partNumber;
  private String key;
  private byte[] bytes;
  private MultipartFile file;
}
