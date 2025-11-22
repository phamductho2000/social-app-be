package com.social.websocket.service.impl;

import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_CHANGE_CONVERSATION;
import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_MESSAGE;

import com.social.websocket.constant.MessageEvent;
import com.social.websocket.dto.request.ChatEvent;
import com.social.websocket.service.ChatWebSocketService;
import com.social.websocket.service.RedisUserConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatWebSocketServiceImpl implements ChatWebSocketService {

  private final SimpMessagingTemplate messagingTemplate;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final RedisUserConversationService redisUserConversationService;

  @Override
  public void sendMessage(ChatEvent<?> chatEvent) {
    MessageEvent event = chatEvent.getEvent();
    if (!event.equals(MessageEvent.TYPING_MESSAGE)) {
      kafkaTemplate.send("chat_message_delivery", chatEvent);
    } else {
      messagingTemplate.convertAndSend(TOPIC_LISTEN_MESSAGE + chatEvent.getConversationId(),
          chatEvent.getPayload());
    }
  }

  @Override
  public void sendMessageEventToClient(String conversationId, Object payload) {
    messagingTemplate.convertAndSend(TOPIC_LISTEN_MESSAGE + conversationId, payload);

    redisUserConversationService.getAll(conversationId).values().forEach(
        r -> messagingTemplate.convertAndSendToUser(r.getUserId(), TOPIC_LISTEN_CHANGE_CONVERSATION,
            payload));
  }
}
