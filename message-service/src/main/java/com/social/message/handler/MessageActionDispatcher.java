package com.social.message.handler;

import com.social.message.constant.MessageEvent;
import com.social.message.dto.ChatEvent;
import com.social.message.exception.ChatServiceException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MessageActionDispatcher {
    private final List<MessageActionHandler<?>> handlers;
    private final Map<MessageEvent, MessageActionHandler<?>> handlerMap = new HashMap<>();

    @PostConstruct
    void init() {
        handlers.forEach(h -> handlerMap.put(h.getEvent(), h));
    }

    @SuppressWarnings("unchecked")
    public <T> Object dispatch(ChatEvent<T> event) throws ChatServiceException {
        MessageActionHandler<T> handler = (MessageActionHandler<T>) handlerMap.get(event.getEvent());
        if (handler != null) {
            return handler.handle(event.getPayload(), event.getConversationId());
        }
        return null;
    }
}
