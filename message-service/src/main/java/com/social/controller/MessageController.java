package com.social.controller;

import com.social.common.dto.ApiResponse;
import com.social.common.page.CustomPageScroll;
import com.social.dto.request.MessageReqDTO;
import com.social.dto.request.SearchMessageRequestDto;
import com.social.dto.response.MessageResDTO;
import com.social.exception.ChatServiceException;
import com.social.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messagesService;

    @PostMapping("/save")
    public ApiResponse<MessageResDTO> saveMessage(@RequestBody MessageReqDTO request) throws ChatServiceException {
        return ApiResponse.success(messagesService.save(request));
    }

    @PostMapping("/get-messages")
    public ApiResponse<CustomPageScroll<MessageResDTO>> getMessages(@RequestBody SearchMessageRequestDto request) throws ChatServiceException {
        return ApiResponse.success(messagesService.getScrollMessages(request));
    }

    @PostMapping("/mark-read-messages")
    public ApiResponse<Boolean> markReadMessages(@RequestBody List<String> ids) throws ChatServiceException {
        return ApiResponse.success(messagesService.markReadMessages(ids));
    }
}
