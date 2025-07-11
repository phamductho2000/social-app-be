package com.social.websocket.domain;

import java.util.List;
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
@RedisHash("WS:CONVERSATION")
public class RedisConversation implements Serializable {
    @Id
    private String conversationId;

    private List<String> userIds;
    private String nodeId;
    private Instant connectedAt;
}
