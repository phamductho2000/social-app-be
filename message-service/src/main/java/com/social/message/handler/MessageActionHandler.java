package com.social.message.handler;

import com.social.message.constant.MessageEvent;
import com.social.message.exception.ChatServiceException;

public interface MessageActionHandler<T> {
    Object handle(T payload, String conversationId) throws ChatServiceException;
    MessageEvent getEvent();
}
