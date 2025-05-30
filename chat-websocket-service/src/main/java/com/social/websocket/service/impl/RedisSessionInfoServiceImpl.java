package com.social.websocket.service.impl;

import com.social.websocket.domain.RedisSessionInfo;
import com.social.websocket.repo.RedisSessionInfoRepository;
import com.social.websocket.service.RedisSessionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RedisSessionInfoServiceImpl implements RedisSessionInfoService {

    private final RedisSessionInfoRepository redisSessionInfoRepository;

    @Override
    public void add(String sessionId, String userId) throws UnknownHostException {
        String nodeId = InetAddress.getLocalHost().getHostName();
        RedisSessionInfo redisSessionInfo = RedisSessionInfo.builder()
                .sessionId(sessionId)
                .userId(userId)
                .connectedAt(Instant.now())
                .nodeId(nodeId)
                .build();
        redisSessionInfoRepository.save(redisSessionInfo);
    }

    @Override
    public void remove(String sessionId) {
        redisSessionInfoRepository.deleteById(sessionId);
    }
}
