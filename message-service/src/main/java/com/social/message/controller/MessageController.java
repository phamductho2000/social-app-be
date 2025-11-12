package com.social.message.controller;

import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.message.dto.request.ReactionHistoryReqDto;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.service.MessageHistoryService;
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

    private final MessageHistoryService messagesService;

//    @PostMapping("/save")
//    public ApiResponse<MessageResDTO> saveMessage(@RequestBody MessageReqDTO request) throws ChatServiceException {
//        return ApiResponse.success(messagesService.save(request));
//    }

    @PostMapping("/search-message")
    public ApiResponse<CustomPageScroll<MessageResDTO>> searchMessage(@RequestBody SearchMessageRequestDto request) throws ChatServiceException {
        return ApiResponse.success(messagesService.searchMessage(request));
    }

    @PostMapping("/mark-read-messages")
    public ApiResponse<Boolean> markReadMessages(@RequestBody List<String> ids) throws ChatServiceException {
        return ApiResponse.success(messagesService.markReadMessages(ids));
    }
}
