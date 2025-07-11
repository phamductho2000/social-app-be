package com.social.websocket.service.impl;

import com.social.websocket.service.RedisConversationService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisConversationServiceImpl implements RedisConversationService {

  private final RedisTemplate<String, String> redisTemplate;

  private static final String key = "WS:CONVERSATION:USERS:";

  @Override
  public void add(String conversationId, String userId) {
    redisTemplate.opsForSet().add(key + conversationId, userId);
  }

  @Override
  public void remove(String conversationId, String userId) {
    redisTemplate.opsForSet().remove(key + conversationId, userId);
  }

  @Override
  public Set<String> getUserIds(String conversationId) {
    return redisTemplate.opsForSet().members(key + conversationId);
  }
}
