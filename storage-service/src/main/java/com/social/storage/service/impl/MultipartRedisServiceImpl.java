package com.social.storage.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.storage.service.MultipartRedisService;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.CompletedPart;

@Service
@RequiredArgsConstructor
public class MultipartRedisServiceImpl implements MultipartRedisService {

  private final RedisTemplate<String, String> redis;

  private static final long TTL_SECONDS = 600; // 10 phút

  private String metaKey(String uploadId) {
    return "multipart:" + uploadId;
  }

  private String partsKey(String uploadId) {
    return "multipart:" + uploadId + ":parts";
  }

  // Lưu metadata ban đầu
  @Override
  public void saveInit(String uploadId, String key) {
    String redisKey = metaKey(uploadId);
    redis.opsForHash().put(redisKey, "key", key);
    redis.opsForHash().put(redisKey, "createdAt", String.valueOf(System.currentTimeMillis()));

    redis.expire(redisKey, TTL_SECONDS, TimeUnit.SECONDS);
  }

  // Lưu part đã upload
  @Override
  public void savePart(String uploadId, CompletedPart part) {
    String json = toJson(part);

    redis.opsForList().rightPush(partsKey(uploadId), json);
    redis.expire(partsKey(uploadId), TTL_SECONDS, TimeUnit.SECONDS);
  }

  // Lấy key S3 để complete upload
  @Override
  public String getKey(String uploadId) {
    return (String) redis.opsForHash().get(metaKey(uploadId), "key");
  }

  // Lấy danh sách tất cả parts để gửi vào CompleteMultipartUploadRequest
  @Override
  public List<CompletedPart> getParts(String uploadId) {
    List<String> jsonList = redis.opsForList().range(partsKey(uploadId), 0, -1);
    if (jsonList == null) {
      return List.of();
    }

    return jsonList.stream()
        .map(this::fromJson)
        .collect(Collectors.toList());
  }

  // Xoá sau complete
  @Override
  public void delete(String uploadId) {
    redis.delete(metaKey(uploadId));
    redis.delete(partsKey(uploadId));
  }

  private String toJson(CompletedPart dto) {
    try {
      return new ObjectMapper().writeValueAsString(dto);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private CompletedPart fromJson(String json) {
    try {
      return new ObjectMapper().readValue(json, CompletedPart.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
