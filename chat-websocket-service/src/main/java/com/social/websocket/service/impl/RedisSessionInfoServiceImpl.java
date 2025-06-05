package com.social.websocket.service.impl;

import com.social.websocket.domain.RedisSessionInfo;
import com.social.websocket.dto.SendMessageDto;
import com.social.websocket.repo.RedisSessionInfoRepository;
import com.social.websocket.service.RedisSessionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RedisSessionInfoServiceImpl implements RedisSessionInfoService {

    private final RedisSessionInfoRepository redisSessionInfoRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void add(String sessionId, String userId, String username) throws UnknownHostException {
        String nodeId = InetAddress.getLocalHost().getHostName();
        RedisSessionInfo redisSessionInfo = RedisSessionInfo.builder()
                .sessionId(sessionId)
                .userId(userId)
                .userName(username)
                .connectedAt(Instant.now())
                .nodeId(nodeId)
                .build();
        redisSessionInfoRepository.save(redisSessionInfo);
    }

    @Override
    public void remove(String sessionId) {
        redisSessionInfoRepository.deleteById(sessionId);
    }

    @Override
    public void send(SendMessageDto request) {
//        List<RedisSessionInfo> redisSessionInfos = redisSessionInfoRepository.findAllByUserIdIn(request.getUserIds());
//        redisSessionInfos.forEach(redisSessionInfo -> {
//            messagingTemplate.convertAndSendToUser(redisSessionInfo.getSessionId(), "/queue/messages", request);
//        });
        messagingTemplate.convertAndSend(request.getTopic(), request.getData());
    }

    @Override
    public RedisSessionInfo get(String sessionId) {
        return redisSessionInfoRepository.findById(sessionId).orElse(null);
    }
}
