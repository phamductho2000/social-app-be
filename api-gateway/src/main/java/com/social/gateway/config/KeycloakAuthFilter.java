//package com.microservices.apigateway.config;
//
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@Component
//public class KeycloakAuthFilter implements WebFilter {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//
//        if (exchange.getRequest().getHeaders().containsKey("Sec-WebSocket-Key")) {
//            return chain.filter(exchange); // Bỏ qua bộ lọc cho yêu cầu WebSocket
//        }
//
//        return exchange.getPrincipal()
//                .cast(Authentication.class)
//                .flatMap(authentication -> {
//                    if (authentication instanceof JwtAuthenticationToken) {
//                        Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
//                        String username = jwt.getClaim("preferred_username");
//
//                        // Tạo request mới với header "Username"
//                        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//                                .header("Username", username)
//                                .build();
//
//                        // Tạo exchange mới với request đã sửa đổi
//                        ServerWebExchange modifiedExchange = exchange.mutate()
//                                .request(modifiedRequest)
//                                .build();
//
//                        return chain.filter(modifiedExchange);
//                    }
//                    return chain.filter(exchange);
//                });
//    }
//}
