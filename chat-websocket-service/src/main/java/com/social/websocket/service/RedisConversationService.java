package com.social.websocket.service;

import java.net.UnknownHostException;
import java.util.Set;

public interface RedisConversationService {
    void add(String conversationId, String userId);

    void remove(String conversationId, String userId);

    Set<String> getUserIds(String conversationId);

//    void send(SendMessageDto request);
}
