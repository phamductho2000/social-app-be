package com.social.websocket.service;

import java.net.UnknownHostException;

public interface RedisSessionInfoService {
    void add(String sessionId, String userId) throws UnknownHostException;

    void remove(String sessionId);
}
