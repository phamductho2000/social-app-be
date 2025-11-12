package com.social.websocket.service;

public interface ChatWebSocketService {

  void typingMessageToConversation(String conversationId, String payload);

  void sendMessageToConversation(String conversationId, String payload) ;

    void reactMessageToConversation(String conversationId, String payload);

  void editMessageToConversation(String conversationId, String payload);

  void replyMessageToConversation(String conversationId, String payload);

  void removeMessageToConversation(String conversationId, String payload);

  void pinMessageToConversation(String conversationId, String payload);

  void sendMessageToAllUser(String conversationId, String payload);
}
