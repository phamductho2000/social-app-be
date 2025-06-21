package com.social.storage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.storage.config.MinioS3Config;
import com.social.storage.dto.request.FileUploadRequestDto;
import com.social.storage.service.S3Service;
import jakarta.annotation.PostConstruct;

import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class S3ServiceImpl implements S3Service {

  private final S3Client s3Client;

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
}
