package com.social.storage.service;

import com.social.storage.dto.request.CompleteChunkRequestDto;
import com.social.storage.dto.request.InitUploadChunkRequestDto;
import com.social.storage.dto.request.UploadChunkRequestDto;
import com.social.storage.dto.response.FileUploadResponseDto;
import com.social.storage.dto.response.UploadChunkResponseDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  FileUploadResponseDto uploadFile(MultipartFile file) throws IOException;

  UploadChunkResponseDto initUploadChunk(InitUploadChunkRequestDto request);

  UploadChunkResponseDto uploadChunk(MultipartFile file, String uploadId, int partNumber)
      throws IOException;

  UploadChunkResponseDto completeUploadChunk(CompleteChunkRequestDto request);
}
