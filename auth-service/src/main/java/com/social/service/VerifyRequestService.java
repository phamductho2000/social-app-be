package com.social.service;

import com.social.conversation.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class VerifyRequestService {
    public static final String HEADER_AUTHORIZATION = "authorization";

    public boolean accept(HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getServletPath();

        if (path.equals("/api/auth/login") || "/v3/api-docs".contains(path) || path.equals("/api/auth/register")) {
            return true;
        }

        String bearerToken = httpServletRequest.getHeader(HEADER_AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring(7); // Cắt bỏ "Bearer "
            JwtUtil.validateToken(accessToken);
            return true;
        }
        return false;
    }
}
