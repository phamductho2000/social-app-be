package com.social.chat.controller;

import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.chat.dto.request.SearchConversationRequestDto;
import com.social.chat.dto.response.UserConversationResDTO;
import com.social.chat.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversation/user-conversation")
@RequiredArgsConstructor
public class UserConversationController {

    private final UserConversationService userConversationService;

    @PostMapping("/search")
    public ApiResponse<CustomPageScroll<UserConversationResDTO>> search(@RequestBody SearchConversationRequestDto request) throws AppException {
        return ApiResponse.success(userConversationService.search(request));
    }
}
