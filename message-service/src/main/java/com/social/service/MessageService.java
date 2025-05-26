package com.social.service;

import com.social.common.page.CustomPageScroll;
import com.social.dto.request.MessageReqDTO;
import com.social.dto.request.SearchMessageRequestDto;
import com.social.dto.response.MessageResDTO;
import com.social.exception.ChatServiceException;

import java.util.List;

public interface MessageService {
   MessageResDTO save(MessageReqDTO reqChatMessageDTO) throws ChatServiceException;

   CustomPageScroll<MessageResDTO> getScrollMessages(SearchMessageRequestDto request) throws ChatServiceException;

   Boolean markReadMessages(List<String> ids) throws ChatServiceException;
}
