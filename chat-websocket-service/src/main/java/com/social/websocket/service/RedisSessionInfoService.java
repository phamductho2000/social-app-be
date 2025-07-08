package com.social.websocket.service;

import com.social.websocket.domain.RedisSessionInfo;

import java.net.UnknownHostException;

public interface RedisSessionInfoService {
    void add(String conversationId, String userId, String username) throws UnknownHostException;

    void remove(String conversationId);

//    void send(SendMessageDto request);

    RedisSessionInfo get(String sessionId);
}
