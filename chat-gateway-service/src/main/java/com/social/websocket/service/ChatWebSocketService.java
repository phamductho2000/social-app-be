package com.social.websocket.service;

import com.social.websocket.dto.request.ChatEvent;

public interface ChatWebSocketService {

  void sendMessage(ChatEvent<?> event) ;

  void sendMessageEventToClient(String conversationId, Object payload);
}
