package com.social.websocket.service;

import com.social.websocket.domain.RedisUserConversation;
import java.util.Map;

public interface RedisUserConversationService {

  void add(String conversationId, String userId);

  void delete(String conversationId, String userId);

  RedisUserConversation get(String conversationId, String userId);

  Map<String, RedisUserConversation> getAll(String conversationId);
}
