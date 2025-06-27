package com.social.storage.controller;

import com.social.common.dto.ApiResponse;
import com.social.storage.dto.response.FileUploadResponseDto;
import com.social.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @PostMapping(value = "/upload")
    public ApiResponse<FileUploadResponseDto> uploadBinaryFile(@RequestParam("file") MultipartFile file)
            throws IOException {
        return ApiResponse.success(storageService.uploadFile(file));
    }
}
