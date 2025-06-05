package com.social.websocket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("WS:SESSION")
public class RedisSessionInfo implements Serializable {
    @Id
    private String sessionId;
    private String userId;
    private String userName;
    private String nodeId;
    private Instant connectedAt;
}
