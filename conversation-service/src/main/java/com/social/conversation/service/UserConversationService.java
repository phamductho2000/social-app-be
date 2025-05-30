package com.social.conversation.service;

import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.conversation.constants.ConversationType;
import com.social.conversation.domain.Conversation;
import com.social.conversation.dto.request.ConversationReqDTO;
import com.social.conversation.dto.request.SearchConversationRequestDto;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.exception.ChatServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserConversationService {

    List<UserConversationResDTO> saveAll(ConversationReqDTO request, Conversation conversation, MessageResDTO messageResDTO) throws AppException;

    CustomPageScroll<UserConversationResDTO> search(SearchConversationRequestDto request) throws AppException;

    List<UserConversationResDTO> handleNewMessage(MessageResDTO request) throws ChatServiceException;
}
