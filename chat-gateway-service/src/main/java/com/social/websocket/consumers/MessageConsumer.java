package com.social.websocket.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.service.ChatWebSocketService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static com.social.websocket.constant.AppConstant.TOPIC_LISTEN_CHANGE_CONVERSATION;
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
//        chatWebSocketService.sendMessageToAllUser(record.key(), objectMapper.writeValueAsString(record.value()));
    }

    @KafkaListener(topics = "UPDATED_CONVERSATION", groupId = "updated_conversation")
    public void listenConversation(ConsumerRecord<String, Object> record) {
        messagingTemplate.convertAndSendToUser(record.key(), TOPIC_LISTEN_CHANGE_CONVERSATION, record.value());
    }
}
