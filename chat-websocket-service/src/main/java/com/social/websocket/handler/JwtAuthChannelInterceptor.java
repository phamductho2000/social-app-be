package com.social.websocket.handler;

import com.social.websocket.verify.JwtVerifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

public class JwtAuthChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        JwtVerifier jwtVerifier = new JwtVerifier();
        assert accessor != null;
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                if (jwtVerifier.validateToken(token)) {
                    String username = jwtVerifier.extractPreferredUsername(token);

                    if (username != null) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(
                                username, null, List.of()
                        );
                        accessor.setUser(auth);
                    }
                }
            }
        }
        return message;
    }
}
