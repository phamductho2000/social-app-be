package com.social.message.service;

import com.social.common.page.CustomPageScroll;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;

import java.util.List;

public interface MessageService {
   MessageResDTO save(MessageReqDTO reqChatMessageDTO) throws ChatServiceException;

   CustomPageScroll<MessageResDTO> getScrollMessages(SearchMessageRequestDto request) throws ChatServiceException;

   Boolean markReadMessages(List<String> ids) throws ChatServiceException;
}
