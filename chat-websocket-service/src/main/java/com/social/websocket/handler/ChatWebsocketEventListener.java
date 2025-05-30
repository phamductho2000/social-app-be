package com.social.websocket.handler;

import com.social.websocket.service.RedisSessionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.UnknownHostException;
import java.util.Objects;

import static org.springframework.messaging.simp.SimpMessageHeaderAccessor.SESSION_ID_HEADER;

@Component
@RequiredArgsConstructor
public class ChatWebsocketEventListener {

    private final RedisSessionInfoService redisSessionInfoService;

    // Xử lý sự kiện kết nối
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) throws UnknownHostException {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        String sessionId = (String) event.getMessage().getHeaders().get(SESSION_ID_HEADER);
        redisSessionInfoService.add(sessionId, userId);
        System.out.println("New connection established: " + sessionId);
    }

    // Xử lý sự kiện ngắt kết nối
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = (String) event.getMessage().getHeaders().get(SESSION_ID_HEADER);
        redisSessionInfoService.remove(sessionId);
        System.out.println("Connection closed: " + sessionId);
    }
}
