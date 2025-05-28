package com.social.conversation.service;

import com.social.conversation.dto.request.ConversationReqDTO;
import com.social.conversation.dto.response.ConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.common.dto.FilterRequest;
import org.springframework.data.domain.Page;

public interface ConversationService {
    ConversationResDTO saveConversation(ConversationReqDTO request) throws ChatServiceException;

    Page<ConversationResDTO> getConversations(FilterRequest request);

    ConversationResDTO getDetailConversation(String conversationId);

    ConversationResDTO createConversation(ConversationReqDTO request) throws ChatServiceException;
}