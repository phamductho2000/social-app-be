package com.social.storage.service;

import com.social.storage.dto.response.FileUploadResponseDto;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  FileUploadResponseDto uploadFile(MultipartFile file) throws IOException;
}
