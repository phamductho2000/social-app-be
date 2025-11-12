package com.social.websocket.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.service.ChatWebSocketService;
import com.social.websocket.service.RedisConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_CHANGE_CONVERSATION;

@Service
@RequiredArgsConstructor
public class ChatWebSocketServiceImpl implements ChatWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RedisConversationService redisConversationService;

    @Override
    public void typingMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("TYPING_MESSAGE", payload);
    }

    @Override
    public void sendMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("SENDING_MESSAGE", payload);
    }

    @Override
    public void reactMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("REACT_MESSAGE", payload);
    }

    @Override
    public void editMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("EDIT_MESSAGE", payload);
    }

    @Override
    public void replyMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("REPLY_MESSAGE", payload);
    }

    @Override
    public void removeMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("REMOVE_MESSAGE", payload);
    }

    @Override
    public void pinMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("PIN_MESSAGE", payload);
    }

    @Override
    public void sendMessageToAllUser(String conversationId, String payload) {
        redisConversationService.getUserIds(conversationId).forEach(userId -> {
            messagingTemplate.convertAndSendToUser(userId, TOPIC_LISTEN_CHANGE_CONVERSATION, payload);
        });
    }
}
