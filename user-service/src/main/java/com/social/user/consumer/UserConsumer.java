package com.social.user.consumer;

import com.social.user.exception.UserServiceException;
import com.social.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConsumer {

    private final UserService userService;

    @KafkaListener(topics = "USER_DISCONNECTED", groupId = "chat-app")
    public void listen(String message) throws UserServiceException {
//        userService.handleUserDisconnected(message);
    }
}
