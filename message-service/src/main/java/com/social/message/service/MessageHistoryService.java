package com.social.message.service;

import com.social.common.page.CustomPageScroll;
import com.social.message.dto.EditMessageDto;
import com.social.message.dto.SendMessageDto;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.ReactionReqDto;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import java.util.List;

public interface MessageHistoryService {
    MessageResDTO create(SendMessageDto request) throws ChatServiceException;

    MessageResDTO react(ReactionReqDto request) throws ChatServiceException;

    MessageResDTO edit(EditMessageDto request) throws ChatServiceException;

    MessageResDTO pin(MessageReqDTO request) throws ChatServiceException;

    CustomPageScroll<MessageResDTO> searchMessage(SearchMessageRequestDto request) throws ChatServiceException;

    Boolean markReadMessages(List<String> ids) throws ChatServiceException;
}
