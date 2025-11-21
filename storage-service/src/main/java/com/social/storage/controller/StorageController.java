package com.social.storage.controller;

import com.social.common.dto.ApiResponse;
import com.social.storage.dto.request.CompleteChunkRequestDto;
import com.social.storage.dto.request.InitUploadChunkRequestDto;
import com.social.storage.dto.request.UploadChunkRequestDto;
import com.social.storage.dto.response.FileUploadResponseDto;
import com.social.storage.dto.response.UploadChunkResponseDto;
import com.social.storage.service.StorageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

  private final StorageService storageService;

  @PostMapping(value = "/upload")
  public ApiResponse<FileUploadResponseDto> uploadBinaryFile(
      @RequestParam("file") MultipartFile file)
      throws IOException {
    return ApiResponse.success(storageService.uploadFile(file));
  }

  @PostMapping(value = "/upload_chunk/init")
  public ApiResponse<UploadChunkResponseDto> initUploadChunk(
      @RequestBody InitUploadChunkRequestDto request) {
    return ApiResponse.success(storageService.initUploadChunk(request));
  }

  @PostMapping(value = "/upload_chunk/part", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ApiResponse<UploadChunkResponseDto> uploadChunk(
      @RequestParam("file") MultipartFile file,
      @RequestParam("uploadId") String uploadId,
      @RequestParam("partNumber") Integer partNumber) throws IOException {
    return ApiResponse.success(storageService.uploadChunk(file, uploadId, partNumber));
  }

  @PostMapping(value = "/upload_chunk/complete")
  public ApiResponse<UploadChunkResponseDto> completeUploadChunk(
      @RequestBody CompleteChunkRequestDto request) {
    return ApiResponse.success(storageService.completeUploadChunk(request));
  }
}
