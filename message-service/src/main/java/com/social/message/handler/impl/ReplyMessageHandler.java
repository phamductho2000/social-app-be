package com.social.message.handler.impl;

import com.social.message.constant.MessageEvent;
import com.social.message.dto.EditMessageDto;
import com.social.message.dto.ReplyMessageDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.handler.MessageActionHandler;
import com.social.message.service.MessageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyMessageHandler implements MessageActionHandler<ReplyMessageDto> {

  private final MessageHistoryService messageHistoryService;

  @Override
  public MessageResDTO handle(ReplyMessageDto message, String conversationId)
      throws ChatServiceException {
    try {
      message.setConversationId(conversationId);
      return messageHistoryService.reply(message);
    } catch (Exception e) {
      throw new ChatServiceException("ERROR");
    }
  }

  @Override
  public MessageEvent getEvent() {
    return MessageEvent.SEND_MESSAGE;
  }
}
