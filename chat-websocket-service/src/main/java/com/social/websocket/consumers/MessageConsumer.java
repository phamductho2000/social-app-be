package com.social.websocket.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.service.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_MESSAGE;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final ObjectMapper objectMapper;

    private final SimpMessagingTemplate messagingTemplate;

    private final ChatWebSocketService chatWebSocketService;

    @KafkaListener(topics = "SENT_MESSAGE", groupId = "sent_message")
    public void listenMessage(ConsumerRecord<String, Object> record) {
        messagingTemplate.convertAndSend(TOPIC_LISTEN_MESSAGE + record.key(), record.value());
    }

    @KafkaListener(topics = "UPDATE_CONVERSATION_SUCCESS", groupId = "chat-app-1")
    public void listenConversation(String payload) {
//        UserConversationResDTO res;
//        try {
//            objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
//            res = objectMapper.readValue(payload, UserConversationResDTO.class);
//            chatWebSocketService.sendConversationChange(List.of(res));
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
    }
}
