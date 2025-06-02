package com.social.websocket.repo;

import com.social.websocket.domain.RedisConversation;
import com.social.websocket.domain.RedisSessionInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedisConversationRepository extends CrudRepository<RedisConversation, String> {

    List<RedisConversation> findAllByUserId(String userId);

    List<RedisConversation> findAllByUserIdIn(List<String> userIds);
}
