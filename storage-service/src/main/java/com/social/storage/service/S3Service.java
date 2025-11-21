package com.social.storage.service;

import com.social.storage.dto.request.FileUploadRequestDto;
import com.social.storage.dto.request.UploadChunkRequestDto;
import java.util.List;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedPart;

public interface S3Service {

  String put(FileUploadRequestDto request);

  String initMultipartUpload(String key, String contentType);

  CompletedPart uploadPart(UploadChunkRequestDto request);

  String generatePresignedUrlForPart(String key, String uploadId, int partNumber);

  CompleteMultipartUploadResponse completeMultipartUpload(List<CompletedPart> completedParts,
      String uploadId, String key);
}
