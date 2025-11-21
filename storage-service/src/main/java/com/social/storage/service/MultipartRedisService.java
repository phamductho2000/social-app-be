package com.social.storage.service;

import java.util.List;
import software.amazon.awssdk.services.s3.model.CompletedPart;

public interface MultipartRedisService {

  void saveInit(String uploadId, String key);

  void savePart(String uploadId, CompletedPart part);

  String getKey(String uploadId);

  List<CompletedPart> getParts(String uploadId);

  void delete(String uploadId);
}
