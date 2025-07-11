package com.social.websocket.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.dto.request.MessageRequestDto;
import com.social.websocket.service.ChatWebSocketService;
import com.social.websocket.service.RedisConversationService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_CHANGE_CONVERSATION;
import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_MESSAGE;

@Service
@RequiredArgsConstructor
public class ChatWebSocketServiceImpl implements ChatWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RedisConversationService redisConversationService;

    @Override
    public void sendMessageToConversation(String conversationId, String payload) {
        kafkaTemplate.send("SENDING_MESSAGE", payload);
    }

    @Override
    public void reactMessageToConversation(String conversationId, String payload) {
        try {
            MessageRequestDto request = objectMapper.readValue(payload, MessageRequestDto.class);
            if (StringUtils.isNotEmpty(request.id())) {
                kafkaTemplate.send("UPDATE_MESSAGE",  payload);
                messagingTemplate.convertAndSend(TOPIC_LISTEN_MESSAGE + request.conversationId(), payload);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendMessageToAllUser(String conversationId, String payload) {
        redisConversationService.getUserIds(conversationId).forEach(userId -> {
            messagingTemplate.convertAndSendToUser(userId, TOPIC_LISTEN_CHANGE_CONVERSATION, payload);
        });
    }
}
