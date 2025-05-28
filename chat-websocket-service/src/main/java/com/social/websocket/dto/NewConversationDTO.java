package com.social.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewConversationDTO {
    private String conversationId;
    private List<MessageDTO> messages;
}
