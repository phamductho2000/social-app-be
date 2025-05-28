package com.social.conversation.service;

import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.conversation.dto.request.SearchConversationRequestDto;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserConversationService {

    List<UserConversationResDTO> saveAll(List<String> participantIds, String conversationId, String type) throws ChatServiceException;

    CustomPageScroll<UserConversationResDTO> search(SearchConversationRequestDto request) throws AppException;

    void handleNewMessage(MessageResDTO request) throws ChatServiceException;
}
