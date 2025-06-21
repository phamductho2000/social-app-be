package com.social.storage.service.impl;

import com.social.storage.dto.request.FileUploadRequestDto;
import com.social.storage.dto.request.FileUploadRequestDto.FileMetadata;
import com.social.storage.dto.response.FileUploadResponseDto;
import com.social.storage.service.S3Service;
import com.social.storage.service.StorageService;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

  private final S3Service s3Service;

  @Override
  public FileUploadResponseDto uploadFile(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return null;
    }

    if (file.getSize() > 10 * 1024 * 1024) {
      return null;
    }

    String contentType = file.getContentType();
    if (!isValidFileType(contentType)) {
      return null;
    }

    // Upload to S3
    byte[] fileBytes = file.getBytes();
    String fileName = file.getOriginalFilename();
    assert fileName != null;
    String extension = fileName.substring(fileName.lastIndexOf("."));
    long size = file.getSize();

    var metadata = FileMetadata.builder()
        .extension(extension)
        .fileSize(String.valueOf(size))
        .fileName(fileName)
        .build();

    FileUploadRequestDto fileUploadRequestDto = FileUploadRequestDto.builder()
        .fileName(fileName)
        .contentType(contentType)
        .fileData(fileBytes)
        .fileMetadata(metadata)
        .build();

    String url = s3Service.put(fileUploadRequestDto);

    return FileUploadResponseDto.builder()
        .fileName(fileName)
        .contentType(contentType)
        .fileSize(size)
        .filePath(url)
        .extension(extension)
        .build();
  }

  private boolean isValidFileType(String contentType) {
    List<String> allowedTypes = Arrays.asList(
        "image/jpeg", "image/png", "image/gif", "image/webp",
        "application/pdf",
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "text/plain"
    );
    return allowedTypes.contains(contentType);
  }
}
