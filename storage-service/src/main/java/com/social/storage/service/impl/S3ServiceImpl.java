package com.social.storage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.storage.config.MinioS3Config;
import com.social.storage.dto.request.FileUploadRequestDto;
import com.social.storage.dto.request.UploadChunkRequestDto;
import com.social.storage.service.S3Service;
import jakarta.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class S3ServiceImpl implements S3Service {

  private final S3Client s3Client;

  private final S3Presigner s3Presigner;

  private final MinioS3Config minioConfig;

  @PostConstruct
  public void createBucketIfNotExists() {
    try {
      s3Client.createBucket(CreateBucketRequest.builder().bucket(minioConfig.getBucket()).build());
      System.out.println("Bucket created: " + minioConfig.getBucket());
    } catch (Exception e) {
      System.err.println("Error creating bucket: " + e.getMessage());
    }
  }

  @Override
  public String put(FileUploadRequestDto request) {
    ObjectMapper mapper = new ObjectMapper();
    Map<String, String> metadata = mapper.convertValue(request.getFileMetadata(), Map.class);
    String key = UUID.randomUUID().toString();
    s3Client.putObject(PutObjectRequest.builder()
            .bucket(minioConfig.getBucket())
            .key(key)
            .contentType(request.getContentType())
            .metadata(metadata)
            .build(),
        RequestBody.fromBytes(request.getFileData()));

    return String.format("%s/%s/%s", minioConfig.getEndpoint(), minioConfig.getBucket(), key);
  }

  @Override
  public String initMultipartUpload(String key, String contentType) {
    try {
      CreateMultipartUploadRequest request = CreateMultipartUploadRequest.builder()
          .bucket(minioConfig.getBucket())
          .key(key)
          .contentType(contentType)
          .build();

      CreateMultipartUploadResponse response = s3Client.createMultipartUpload(request);

      return response.uploadId();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public CompletedPart uploadPart(UploadChunkRequestDto request) {
    try {
      UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
          .bucket(minioConfig.getBucket())
          .key(request.getKey())
          .uploadId(request.getUploadId())
          .partNumber(request.getPartNumber())
          .contentLength((long) request.getBytes().length)
          .build();

      UploadPartResponse response = s3Client.uploadPart(uploadPartRequest,
          RequestBody.fromBytes(request.getBytes()));

      return CompletedPart.builder()
          .partNumber(request.getPartNumber())
          .eTag(response.eTag())
          .build();
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String generatePresignedUrlForPart(String key, String uploadId, int partNumber) {
    UploadPartRequest partRequest = UploadPartRequest.builder()
        .bucket(minioConfig.getBucket())
        .key(key)
        .uploadId(uploadId)
        .partNumber(partNumber)
        .build();

    UploadPartPresignRequest presignRequest = UploadPartPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(10))
        .uploadPartRequest(partRequest)
        .build();

    PresignedUploadPartRequest presignedRequest = s3Presigner.presignUploadPart(presignRequest);

    return presignedRequest.url().toString();
  }

  @Override
  public CompleteMultipartUploadResponse completeMultipartUpload(List<CompletedPart> completedParts,
      String uploadId, String key) {
    try {
      CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
          .parts(completedParts)
          .build();

      CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
          .bucket(minioConfig.getBucket())
          .key(key)
          .uploadId(uploadId)
          .multipartUpload(completedUpload)
          .build();

      return s3Client.completeMultipartUpload(completeRequest);
    } catch (Exception e) {
      throw e;
    }
  }
}
