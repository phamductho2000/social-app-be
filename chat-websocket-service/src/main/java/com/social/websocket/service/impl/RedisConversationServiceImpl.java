package com.social.websocket.service.impl;

import com.social.websocket.domain.RedisConversation;
import com.social.websocket.repo.RedisConversationRepository;
import com.social.websocket.service.RedisConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RedisConversationServiceImpl implements RedisConversationService {

    private final RedisConversationRepository redisConversationRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void add(String conversationId, String userId) throws UnknownHostException {
        String nodeId = InetAddress.getLocalHost().getHostName();
        RedisConversation redisConversation = RedisConversation.builder()
                .conversationId(conversationId)
                .userId(userId)
                .connectedAt(Instant.now())
                .nodeId(nodeId)
                .build();
        redisConversationRepository.save(redisConversation);
    }

    @Override
    public void remove(String conversationId) {
        redisConversationRepository.deleteById(conversationId);
    }

//    @Override
//    public void send(SendMessageDto request) {
////        List<RedisSessionInfo> redisSessionInfos = redisSessionInfoRepository.findAllByUserIdIn(request.getUserIds());
////        redisSessionInfos.forEach(redisSessionInfo -> {
////            messagingTemplate.convertAndSendToUser(redisSessionInfo.getSessionId(), "/queue/messages", request);
////        });
//        messagingTemplate.convertAndSend(request.getTopic(), request.getData());
//    }
}
