package com.social.chat.controller;

import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.chat.dto.request.ConversationReqDTO;
import com.social.chat.dto.request.SearchConversationRequestDto;
import com.social.chat.dto.response.ConversationResDTO;
import com.social.chat.dto.response.UserConversationResDTO;
import com.social.chat.service.ConversationService;
import com.social.common.dto.ApiResponse;
import com.social.common.dto.FilterRequest;
import com.social.chat.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationsService;

    private final UserConversationService userConversationService;

    @PostMapping("/save")
    public ApiResponse<ConversationResDTO> saveConversation(@RequestBody ConversationReqDTO request) throws AppException {
        return ApiResponse.success(conversationsService.saveConversation(request));
    }

    @PostMapping("/create")
    public ApiResponse<ConversationResDTO> createConversation(@RequestBody ConversationReqDTO request) throws AppException {
        return ApiResponse.success(conversationsService.createConversation(request));
    }

    @PostMapping("/get-conversations")
    public ApiResponse<Page<ConversationResDTO>> getConversation(@RequestBody FilterRequest request) {
        return ApiResponse.success(conversationsService.getConversations(request));
    }

//    @PostMapping("/get-conversations-messages/{userId}")
//    public ApiResponse<Page<ConversationResDTO>> getConversationsWithMessages(@PathVariable String userId, @ParameterObject Pageable pageable) {
//        return conversationsService.getConversationsWithMessages(userId, pageable);
//    }

    @GetMapping("/get-detail-conversation/{conversationId}")
    public ApiResponse<ConversationResDTO> getDetailConversation(@PathVariable String conversationId) {
        return ApiResponse.success(conversationsService.getDetailConversation(conversationId));
    }

    @PostMapping("/search-user-conversation")
    public ApiResponse<CustomPageScroll<UserConversationResDTO>> searchUserConversation(@RequestBody SearchConversationRequestDto request) throws AppException {
        return ApiResponse.success(userConversationService.search(request));
    }
}
