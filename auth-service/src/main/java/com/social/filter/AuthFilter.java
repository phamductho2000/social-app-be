package com.social.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.service.VerifyRequestService;
import com.social.conversation.dto.ApiResponse;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.social.constants.RespCode.ERR_AUTH_REQUEST_IS_NOT_ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {
    private final VerifyRequestService verifyRequestService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (verifyRequestService.accept((HttpServletRequest) servletRequest)) {
            System.out.println("di qua");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("k di qua");
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());
            servletResponse.setContentType(APPLICATION_JSON_VALUE);
            servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            servletResponse.getWriter().write(new ObjectMapper().writeValueAsString(ApiResponse.error(ERR_AUTH_REQUEST_IS_NOT_ACCEPTED)));
        }
    }
}
