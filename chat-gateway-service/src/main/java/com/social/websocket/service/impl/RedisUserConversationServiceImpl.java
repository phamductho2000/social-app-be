package com.social.websocket.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.websocket.domain.RedisUserConversation;
import com.social.websocket.service.RedisUserConversationService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisUserConversationServiceImpl implements RedisUserConversationService {

  private final RedisTemplate<String, RedisUserConversation> redisTemplate;

  private final ObjectMapper objectMapper;

  @Override
  public void add(String conversationId, String userId) {
    String key = "chat:conversation:" + conversationId + ":members";

    RedisUserConversation info = RedisUserConversation.builder()
        .userId(userId)
        .build();

    try {
      String json = objectMapper.writeValueAsString(info);
      redisTemplate.opsForHash().put(key, userId, json);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(String conversationId, String userId) {
    String key = "chat:conversation:" + conversationId + ":members";
    redisTemplate.opsForHash().delete(key, userId);
  }

  @Override
  public RedisUserConversation get(String conversationId, String userId) {
    String key = "chat:conversation:" + conversationId + ":members";
    String json = (String) redisTemplate.opsForHash().get(key, userId);
    if (json == null) {
      return null;
    }

    try {
      return objectMapper.readValue(json, RedisUserConversation.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Map<String, RedisUserConversation> getAll(String conversationId) {
    String key = "chat:conversation:" + conversationId + ":members";

    Map<Object, Object> raw = redisTemplate.opsForHash().entries(key);

    Map<String, RedisUserConversation> result = new HashMap<>();
    raw.forEach((k, v) -> {
      try {
        result.put((String) k,
            objectMapper.readValue((String) v, RedisUserConversation.class)
        );
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    return result;
  }
}
