package com.social.conversation.service;

import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserConversationService {

    List<UserConversationResDTO> saveAll(List<String> participantIds, String conversationId, String type) throws ChatServiceException;

    Page<UserConversationResDTO> getUserConversations(String userId, Pageable pageable);

    void handleNewMessage(MessageResDTO messageResDTO) throws ChatServiceException;
}
