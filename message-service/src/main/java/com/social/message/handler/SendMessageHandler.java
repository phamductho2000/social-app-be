package com.social.message.handler;

import com.social.message.constant.MessageEvent;
import com.social.message.dto.SendMessageDto;
import com.social.message.service.MessageHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMessageHandler implements MessageActionHandler<SendMessageDto> {

    private final MessageHistoryService messageHistoryService;

    @Override
    public void handle(SendMessageDto message, String conversationId) {
        messageHistoryService.create(message);
    }

    @Override
    public MessageEvent getEvent() {
        return MessageEvent.SEND_MESSAGE;
    }
}
