package com.social.websocket.handler;

import com.social.websocket.verify.JwtVerifier;
import java.util.List;
import java.util.Map;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class JwtAuthChannelInterceptor implements ChannelInterceptor {

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
        StompHeaderAccessor.class);

    JwtVerifier jwtVerifier = new JwtVerifier();
    assert accessor != null;
    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      String token = accessor.getFirstNativeHeader("Authorization");

      if (token != null && token.startsWith("Bearer ")) {
        token = token.substring(7);

        if (jwtVerifier.validateToken(token)) {

          String username = jwtVerifier.extractPreferredUsername(token);
          String subId = jwtVerifier.extractSubject(token);

          Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
          assert sessionAttributes != null;
          sessionAttributes.put("username", username);
          sessionAttributes.put("userid", subId);

          if (subId != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(
                subId, null, List.of()
            );
            accessor.setUser(auth);
          }
        }
      }
    }
    return message;
  }
}
