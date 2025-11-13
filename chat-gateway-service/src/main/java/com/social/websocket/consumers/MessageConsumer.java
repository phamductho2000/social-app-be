package com.social.websocket.consumers;

import com.social.websocket.service.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

  private final ChatWebSocketService chatWebSocketService;

  @KafkaListener(topics = "chat_message_delivery", groupId = "chat-gateway-service")
  public void listenMessage(ConsumerRecord<String, Object> record) {
    chatWebSocketService.sendMessageEventToClient(record.key(), record.value());
  }
}
