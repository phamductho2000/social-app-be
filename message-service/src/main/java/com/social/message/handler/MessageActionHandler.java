package com.social.message.handler;

import com.social.message.constant.MessageEvent;
import com.social.message.dto.ChatEvent;

public interface MessageActionHandler {
    void handle(ChatEvent event);
    MessageEvent getEvent();
}
