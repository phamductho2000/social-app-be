package com.social.websocket.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.dto.UserConversationResDTO;
import com.social.websocket.service.ChatWebSocketService;
import com.social.websocket.service.RedisSessionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatWebSocketServiceImpl implements ChatWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RedisSessionInfoService redisSessionInfoService;

    @Override
    public void sendMessage(Message<Object> message) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        Object payload = message.getPayload();

        if (payload instanceof byte[]) {
            try {
                String json = new String((byte[]) payload, StandardCharsets.UTF_8);
//
//                ObjectMapper mapper = new ObjectMapper();
//                MessageDTO chatMessage = mapper.readValue(json, MessageDTO.class);

                kafkaTemplate.send("SENDING_MESSAGE", json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendConversationChange(List<UserConversationResDTO> conversations) {
        conversations.forEach(conversation -> {
            messagingTemplate.convertAndSendToUser(conversation.getUsername(), "/queue/conversation", conversation);
        });
    }
}
