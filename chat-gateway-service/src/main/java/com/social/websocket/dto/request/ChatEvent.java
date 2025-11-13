package com.social.websocket.dto.request;

import com.social.websocket.constant.MessageEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatEvent<T> {
    private MessageEvent event;
    private String conversationId;
    private T payload;
}
