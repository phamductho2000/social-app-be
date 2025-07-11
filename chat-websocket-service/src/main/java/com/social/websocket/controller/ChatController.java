package com.social.websocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.websocket.service.ChatWebSocketService;
import com.social.websocket.service.RedisConversationService;
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

    private final RedisConversationService redisConversationService;

    @MessageMapping("/request-online-status")
    public void handleOnlineStatusRequest(String message, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        // Gửi danh sách user online tới client
//        messagingTemplate.convertAndSend("/topic/status-users", Objects.requireNonNull(redisService.getDataSet("USER_ONLINE")));
    }

    @MessageMapping("/connect-conversation/{conversationId}")
    public void connectConversation(@DestinationVariable String conversationId, String userId) {
        redisConversationService.add(conversationId, userId);
    }

    @MessageMapping("/disconnect-conversation/{conversationId}")
    public void disconnectConversation(@DestinationVariable String conversationId, String userId) {
        redisConversationService.remove(conversationId, userId);
    }

    @MessageMapping("/chat/message/send/{conversationId}")
    public void sendMessageToConversation(@DestinationVariable String conversationId, String message) {
        chatWebSocketService.sendMessageToConversation(conversationId, message);
    }

    @MessageMapping("/chat/message/react/{conversationId}")
    public void reactMessageToConversation(@DestinationVariable String conversationId, String message) {
        chatWebSocketService.reactMessageToConversation(conversationId, message);
    }
}
