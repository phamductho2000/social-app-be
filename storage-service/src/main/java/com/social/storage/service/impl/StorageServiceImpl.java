package com.social.storage.service.impl;

import com.social.storage.dto.request.CompleteChunkRequestDto;
import com.social.storage.dto.request.FileUploadRequestDto;
import com.social.storage.dto.request.FileUploadRequestDto.FileMetadata;
import com.social.storage.dto.request.InitUploadChunkRequestDto;
import com.social.storage.dto.request.UploadChunkRequestDto;
import com.social.storage.dto.response.FileUploadResponseDto;
import com.social.storage.dto.response.UploadChunkResponseDto;
import com.social.storage.dto.response.UploadChunkResponseDto.ChunkUrl;
import com.social.storage.service.MultipartRedisService;
import com.social.storage.service.S3Service;
import com.social.storage.service.StorageService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.CompletedPart;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

  private final S3Service s3Service;

  private final MultipartRedisService multipartRedisService;

  private static final int MAX_PARTS = 10000;

  private static final int MIN_CHUNK_SIZE = 5 * 1024 * 1024;

  private static final int MAX_CHUNK_SIZE = 100 * 1024 * 1024;

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

  @Override
  public UploadChunkResponseDto initUploadChunk(InitUploadChunkRequestDto request) {
    String key = UUID.randomUUID().toString();
    String uploadId = s3Service.initMultipartUpload(key, request.contentType());
    long size = request.fileSize();
    long chunkSize = calculateChunkSize(size);
    int totalChunks = (int) Math.ceil((double) size / chunkSize);

    List<ChunkUrl> chunkUrls = new ArrayList<>();
    for (int i = 0; i < totalChunks; i++) {
      int partNumber = i + 1;
      String url = s3Service.generatePresignedUrlForPart(key, uploadId, partNumber);
      chunkUrls.add(new ChunkUrl(partNumber, url));
    }

    multipartRedisService.saveInit(uploadId, key);
    return UploadChunkResponseDto.builder()
        .uploadId(uploadId)
        .totalChunks(totalChunks)
        .chunkSize(chunkSize)
        .chunkUrls(chunkUrls)
        .build();
  }

  @Override
  public UploadChunkResponseDto uploadChunk(MultipartFile file, String uploadId, int partNumber)
      throws IOException {
    try {
      String key = multipartRedisService.getKey(uploadId);

      CompletedPart part = s3Service.uploadPart(UploadChunkRequestDto.builder()
          .key(key)
          .uploadId(uploadId)
          .partNumber(partNumber)
          .bytes(file.getBytes())
          .build());

      multipartRedisService.savePart(uploadId, part);

      return UploadChunkResponseDto.builder()
          .uploadId(uploadId)
//          .partNumber(partNumber)
          .build();

    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public UploadChunkResponseDto completeUploadChunk(CompleteChunkRequestDto request) {
    List<CompletedPart> parts = request.parts().stream()
        .map(m -> CompletedPart.builder()
            .partNumber(m.partNumber())
            .eTag(m.etag())
            .build())
        .collect(Collectors.toList());

    String key = multipartRedisService.getKey(request.uploadId());

    s3Service.completeMultipartUpload(parts, request.uploadId(), key);

    return UploadChunkResponseDto.builder()
        .uploadId(request.uploadId())
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

  private long calculateChunkSize(long size) {
    long chunkSize = (long) Math.ceil((double) size / MAX_PARTS);

    if (chunkSize < MIN_CHUNK_SIZE) {
      chunkSize = MIN_CHUNK_SIZE;
    }

    if (chunkSize > MAX_CHUNK_SIZE) {
      chunkSize = MAX_CHUNK_SIZE;
    }

    return chunkSize;
  }
}
