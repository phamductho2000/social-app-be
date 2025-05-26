package com.social.conversation.service;

import com.social.conversation.dto.request.ConversationReqDTO;
import com.social.conversation.dto.response.ConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.dto.FilterRequest;
import org.springframework.data.domain.Page;

public interface ConversationService {
    ConversationResDTO saveConversation(ConversationReqDTO conversationsReqDTO) throws ChatServiceException;

    Page<ConversationResDTO> getConversations(FilterRequest request);

    ConversationResDTO getDetailConversation(String conversationId);

    ConversationResDTO createConversation(ConversationReqDTO req) throws ChatServiceException;
}