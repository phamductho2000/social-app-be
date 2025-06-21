package com.social.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtWebFilter implements WebFilter {

    private final JwtVerifier jwtVerifier;

    private final List<String> whitelist = List.of("/api/auth/register", "/api/auth/login", "/api/auth/refreshToken");
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String token = "";

        if (path.startsWith("/ws")) {
            token = exchange.getRequest().getQueryParams().getFirst("token");
        } else {
            if (whitelist.contains(path)) {
                return chain.filter(exchange);
            }
            String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (StringUtils.isNotEmpty(authHeader)) {
                token =  authHeader.substring(7);
            }
        }

        if (StringUtils.isNotEmpty(token)) {

            if (jwtVerifier.validateToken(token)) {

                ServerHttpRequest mutatedRequest = exchange.getRequest()
                        .mutate()
                        .header("userid", jwtVerifier.extractSubject(token))
                        .header("username", jwtVerifier.extractPreferredUsername(token))
                        .build();

                ServerWebExchange mutatedExchange = exchange.mutate()
                        .request(mutatedRequest)
                        .build();

                return chain.filter(mutatedExchange);
            }

            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap("Invalid JWT: ".getBytes());
            return exchange.getResponse().setComplete();
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap("Invalid JWT: ".getBytes());
        return exchange.getResponse().setComplete();
    }
}

