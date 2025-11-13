package com.social.message.consumers;

import com.social.message.dto.ChatEvent;
import com.social.message.handler.MessageActionDispatcher;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final MessageActionDispatcher dispatcher;

    @KafkaListener(topics = "chat_message_event", groupId = "message-service")
    public void listenSending(ChatEvent<?> event) {
        try {
            Object res = dispatcher.dispatch(event);

            ProducerRecord<String, Object> record = new ProducerRecord<>(
                "chat_message_delivery",
                event.getConversationId(),
                res
            );

            kafkaTemplate.send(record);
        } catch (Exception e) {
//            kafkaTemplate.send("SENDING_MESSAGE_FAILED", payload);
            throw new RuntimeException(e);
        }
    }
}
