package com.social.message.handler;

import com.social.message.constant.MessageEvent;

public interface MessageActionHandler<T> {
    void handle(T payload, String conversationId);
    MessageEvent getEvent();
}
