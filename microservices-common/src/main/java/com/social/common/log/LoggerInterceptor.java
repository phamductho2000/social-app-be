package com.social.common.log;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Order(1)
public class LoggerInterceptor implements Filter {

    private final Logger logger;

//    @Override
//    public boolean doFilter(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        System.out.println("preHandle");
//        logger.addServletRequest(request);
//        return true; // cho phép đi tiếp
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("preHandle");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        logger.addServletRequest(httpRequest);
        filterChain.doFilter(request, response);
    }
}
