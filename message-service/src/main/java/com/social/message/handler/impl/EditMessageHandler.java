package com.social.message.handler.impl;

import com.social.message.constant.MessageEvent;
import com.social.message.dto.EditMessageDto;
import com.social.message.dto.SendMessageDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.handler.MessageActionHandler;
import com.social.message.service.MessageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EditMessageHandler implements MessageActionHandler<EditMessageDto> {

  private final MessageHistoryService messageHistoryService;

  @Override
  public MessageResDTO handle(EditMessageDto message, String conversationId)
      throws ChatServiceException {
    try {
      message.setConversationId(conversationId);
      return messageHistoryService.edit(message);
    } catch (Exception e) {
      throw new ChatServiceException("ERROR");
    }
  }

  @Override
  public MessageEvent getEvent() {
    return MessageEvent.SEND_MESSAGE;
  }
}
