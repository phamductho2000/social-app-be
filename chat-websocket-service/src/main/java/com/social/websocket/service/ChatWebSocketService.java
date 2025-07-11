package com.social.websocket.service;

public interface ChatWebSocketService {
    void sendMessageToConversation(String conversationId, String message) ;

    void reactMessageToConversation(String conversationId, String payload);

    void sendMessageToAllUser(String conversationId, String payload);
}
