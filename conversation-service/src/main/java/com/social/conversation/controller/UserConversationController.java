package com.social.conversation.controller;

import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.conversation.service.UserConversationService;
import com.social.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/conversation")
@RequiredArgsConstructor
public class UserConversationController {

    private final UserConversationService userConversationService;

    @PostMapping("/get-user-conversations/{userId}")
    public ApiResponse<Page<UserConversationResDTO>> getUserConversations(@PathVariable String userId, @ParameterObject Pageable pageable) throws ChatServiceException {
        return ApiResponse.success(userConversationService.getUserConversations(userId, pageable));
    }
}
