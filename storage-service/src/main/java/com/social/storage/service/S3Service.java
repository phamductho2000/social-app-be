package com.social.storage.service;

import com.social.storage.dto.request.FileUploadRequestDto;

public interface S3Service {

  String put(FileUploadRequestDto request);
}
