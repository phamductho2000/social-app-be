package com.social.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.websocket.service.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatWebSocketService chatWebSocketService;

    private final RedisTemplate<String, Object> redisTemplate;

    @MessageMapping("/request-online-status")
    public void handleOnlineStatusRequest(String message, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        // Gửi danh sách user online tới client
//        messagingTemplate.convertAndSend("/topic/status-users", Objects.requireNonNull(redisService.getDataSet("USER_ONLINE")));
    }

    @MessageMapping("/connect-conversation/{conversationId}")
    public void connectConversation(@DestinationVariable String conversationId, String userId) {
        redisTemplate.opsForSet().add(String.format("CONNECT_CONVERSATION:%s", conversationId), userId);
    }

    @MessageMapping("/disconnect-conversation/{conversationId}")
    public void disconnectConversation(@DestinationVariable String conversationId, String userId) {
        redisTemplate.opsForSet().remove(String.format("CONNECT_CONVERSATION:%s", conversationId), userId);
    }

    @MessageMapping("/chat/message/send")
    public void sendMessageToConversation(String message) throws JsonProcessingException {
        chatWebSocketService.sendMessage(message);
    }

    @MessageMapping("/chat/message/react")
    public void reactMessageToConversation(String message) throws JsonProcessingException {
        chatWebSocketService.sendMessage(message);
    }
}
