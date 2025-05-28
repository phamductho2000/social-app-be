package com.social.websocket.domain;

import com.social.websocket.constant.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("WS_USER_CONNECT")
public class WsUserConnect implements Serializable {
    @Id
    private String userId;
    private String username;
    private UserStatus status;
    private LocalDateTime timeConnected;
    private LocalDateTime timeDisconnected;
}
