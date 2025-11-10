package com.social.message.dto;

import com.social.message.constant.MessageEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatEvent {
    private MessageEvent event;
    private String conversationId;
    private Object payload;
}
