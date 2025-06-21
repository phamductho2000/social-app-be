package com.social.storage.controller;

import com.social.storage.service.StorageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/storage/api")
@RequiredArgsConstructor
public class StorageController {

  private final StorageService storageService;

  @PostMapping(value = "/upload")
  public ResponseEntity<?> uploadBinaryFile(@RequestParam("file") MultipartFile file)
      throws IOException {
    return ResponseEntity.ok(storageService.uploadFile(file));
  }
}
