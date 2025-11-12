package com.social.chat.service;

import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.chat.domain.Conversation;
import com.social.chat.dto.request.MarkReadMessageReqDto;
import com.social.chat.dto.request.SearchConversationRequestDto;
import com.social.chat.dto.response.MessageResDTO;
import com.social.chat.dto.response.UserConversationResDTO;
import com.social.chat.dto.response.UserResponseDTO;
import com.social.chat.exception.ChatServiceException;

import java.util.List;

public interface UserConversationService {

    List<UserConversationResDTO> saveAll(List<UserResponseDTO> participants, Conversation conversation, MessageResDTO messageResDTO) throws AppException;

    CustomPageScroll<UserConversationResDTO> search(SearchConversationRequestDto request) throws AppException;

    List<UserConversationResDTO> handleNewMessage(MessageResDTO request) throws ChatServiceException;

    void updateMarkRead(MarkReadMessageReqDto request) throws AppException;
}
