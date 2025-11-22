package com.social.websocket.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisUserConversation implements Serializable {

  private String userId;
  private String userName;
  private String displayName;
}
