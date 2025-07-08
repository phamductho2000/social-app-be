package com.social.websocket.service;

import java.net.UnknownHostException;

public interface RedisConversationService {
    void add(String sessionId, String userId) throws UnknownHostException;

    void remove(String sessionId);

//    void send(SendMessageDto request);
}
