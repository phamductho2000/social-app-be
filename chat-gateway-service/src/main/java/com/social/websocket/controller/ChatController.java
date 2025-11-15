package com.social.websocket.controller;

import com.social.websocket.dto.request.ChatEvent;
import com.social.websocket.service.ChatWebSocketService;
import com.social.websocket.service.RedisUserConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatWebSocketService chatWebSocketService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisUserConversationService redisUserConversationService;

    @MessageMapping("/request-online-status")
    public void handleOnlineStatusRequest(String message, SimpMessageHeaderAccessor headerAccessor) {
        String userId = (String) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId");
        // Gửi danh sách user online tới client
//        messagingTemplate.convertAndSend("/topic/status-users", Objects.requireNonNull(redisService.getDataSet("USER_ONLINE")));
    }

    @MessageMapping("/connect-conversation/{conversationId}")
    public void connectConversation(@DestinationVariable String conversationId, Principal principal) {
        String userId = principal.getName();
        redisUserConversationService.add(conversationId, userId);
    }

    @MessageMapping("/disconnect-conversation/{conversationId}")
    public void disconnectConversation(@DestinationVariable String conversationId, Principal principal) {
        String userId = principal.getName();
        redisUserConversationService.delete(conversationId, userId);
    }

    @MessageMapping("/chat/message/send")
    public void sendMessageToConversation(ChatEvent<?> event) {
        chatWebSocketService.sendMessage(event);
    }
}
