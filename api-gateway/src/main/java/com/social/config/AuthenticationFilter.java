//package com.microservices.apigateway.config;
//
//import com.microservices.apigateway.util.JwtUtil;
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
//
//    @Autowired
//    private RouterValidator routerValidator;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    public AuthenticationFilter() {
//        super(Config.class);
//    }
//
////    @Override
////    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
////        ServerHttpRequest request = exchange.getRequest();
////
////        if (routerValidator.isSecured.test(request)) {
////            if (this.isAuthMissing(request))
////                return this.onError(exchange, "Authorization header is missing in request");
////
////            final String token = this.getAuthHeader(request);
////
////            if (jwtUtil.isInvalid(token))
////                return this.onError(exchange, "Authorization header is invalid");
////
////            this.populateRequestWithHeaders(exchange, token);
////        }
////        return chain.filter(exchange);
////    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            if (routerValidator.isSecured.test(request)) {
//                if (this.isAuthMissing(request))
//                    return this.onError(exchange, "Authorization header is missing in request");
//
//                String authHeader = this.getAuthHeader(request);
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    String token = authHeader.substring(7);
//                    if (jwtUtil.isInvalid(token))
//                        return this.onError(exchange, "Authorization header is invalid");
//                }
//
////                this.populateRequestWithHeaders(exchange, token);
//            }
//            return chain.filter(exchange);
//        });
//    }
//
//    private Mono<Void> onError(ServerWebExchange exchange, String err) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(HttpStatus.UNAUTHORIZED);
//        return response.setComplete();
//    }
//
//    private String getAuthHeader(ServerHttpRequest request) {
//        return request.getHeaders().getOrEmpty("Authorization").get(0);
//    }
//
//    private boolean isAuthMissing(ServerHttpRequest request) {
//        return !request.getHeaders().containsKey("Authorization");
//    }
//
//    public static class Config {
//
//    }
//}
