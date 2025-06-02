package com.social.websocket.repo;

import com.social.websocket.domain.RedisSessionInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RedisSessionInfoRepository extends CrudRepository<RedisSessionInfo, String> {

    List<RedisSessionInfo> findAllByUserId(String userId);

    List<RedisSessionInfo> findAllByUserIdIn(List<String> userIds);
}
