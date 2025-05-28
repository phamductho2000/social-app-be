package com.social.websocket.handler;

import com.social.websocket.repo.WsUserConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ChatWebsocketEventListener {

    private final RedisTemplate<String, Object> redisTemplate;

    private final SimpMessagingTemplate messagingTemplate;

    private final WsUserConnectRepository wsUserConnectRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    // Xử lý sự kiện kết nối
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        System.out.println("New connection established: " + userId);
//        WsUserConnect value = WsUserConnect.builder()
//                .timeConnected(LocalDateTime.now())
//                .status(UserStatus.ACTIVE)
//                .userId(userId)
//                .build();
//        wsUserConnectRepository.save(value);
        redisTemplate.opsForSet().add("USER_ONLINE", userId);
//        messagingTemplate.convertAndSend("/topic/status-users", redisService.getDataSet("USER_ONLINE"));
    }

    // Xử lý sự kiện ngắt kết nối
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        System.out.println("Connection closed: " + userId);
//        WsUserConnect value = WsUserConnect.builder()
//                .timeDisconnected(LocalDateTime.now())
//                .status(UserStatus.INACTIVE)
//                .userId(userId)
//                .build();
//        wsUserConnectRepository.save(value);
        redisTemplate.opsForSet().remove("USER_ONLINE", userId);
        kafkaTemplate.send("USER_DISCONNECTED", userId);
//        messagingTemplate.convertAndSend("/topic/status-users", redisService.getDataSet("USER_ONLINE"));
    }
}
