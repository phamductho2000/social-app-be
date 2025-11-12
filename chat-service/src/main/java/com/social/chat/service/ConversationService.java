package com.social.chat.service;

import com.social.common.exception.AppException;
import com.social.chat.dto.request.ConversationReqDTO;
import com.social.chat.dto.response.ConversationResDTO;
import com.social.common.dto.FilterRequest;
import org.springframework.data.domain.Page;

public interface ConversationService {
    ConversationResDTO saveConversation(ConversationReqDTO request) throws AppException;

    Page<ConversationResDTO> getConversations(FilterRequest request);

    ConversationResDTO getDetailConversation(String conversationId);

    ConversationResDTO createConversation(ConversationReqDTO request) throws AppException;
}